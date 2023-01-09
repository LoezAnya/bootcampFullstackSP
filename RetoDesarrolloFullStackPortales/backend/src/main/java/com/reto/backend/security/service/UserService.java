package com.reto.backend.security.service;

import com.reto.backend.security.entity.User;
import com.reto.backend.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;
    public Optional<User> getByUserName(String userName){
        return this.userRepository.findByUsername(userName);
    }

    public boolean existsByUserName(String userName){
        return this.userRepository.existsByUsername(userName);
    }

    public boolean existsByEmail(String email){
       return this.userRepository.existsByEmail(email);
    }

    public void save(User user){
        this.userRepository.save(user);
    }

}
