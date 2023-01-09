package com.reto.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsService userDetailsService;
    private final static Logger logger= LoggerFactory.getLogger(JwtTokenFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token=this.getToken(request);
            if(token!= null && jwtProvider.validateToken(token)){
                String userName= jwtProvider.getUserNameFromToken(token);
                UserDetails userDetails= userDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken auth= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (Exception e){
            logger.error("Fail Method doFilter");
        }
        filterChain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request){
        String hearder=request.getHeader("Authorization");
        if(hearder != null && hearder.startsWith("Bearer")){
            return hearder.replace("Bearer","");
        }
        return null;
    }
}
