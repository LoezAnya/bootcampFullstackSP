package com.reto.backend.security.controller;

import com.reto.backend.security.entity.Role;
import com.reto.backend.security.entity.User;
import com.reto.backend.security.enums.ERole;
import com.reto.backend.security.jwt.JwtProvider;
import com.reto.backend.security.payload.request.JwtDTO;
import com.reto.backend.security.payload.request.SingUpResquest;
import com.reto.backend.security.payload.request.LoginRequest;
import com.reto.backend.security.payload.response.MessageResponse;
import com.reto.backend.security.service.RoleService;
import com.reto.backend.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpResques(@Valid  @RequestBody SingUpResquest singUpResquest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new MessageResponse("Missplaced Attributes"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUserName(singUpResquest.getUsername())) {
            return new ResponseEntity<>(new MessageResponse("Username already in use"), HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(singUpResquest.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("Email already in use"), HttpStatus.BAD_REQUEST);
        }

        User user= new User(singUpResquest.getName(),singUpResquest.getUsername(),singUpResquest.getEmail(),passwordEncoder.encode(singUpResquest.getPassword()));

        Set<Role> roles =new HashSet<>();

        roles.add(roleService.getByERole(ERole.ROLE_USER).get());
        if(singUpResquest.getRoles().contains("admin")){
            roles.add(roleService.getByERole(ERole.ROLE_ADMIN).get());
        }
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new MessageResponse("User Created"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO>LoginRequest(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new MessageResponse("Missplaced Attributes"), HttpStatus.BAD_REQUEST);
        }

        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= jwtProvider.generateToken(authentication);
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        JwtDTO jwtDTO=new JwtDTO(jwt,userDetails.getUsername(),userDetails.getAuthorities());

        return new ResponseEntity<>(jwtDTO,HttpStatus.OK);
    }
}
