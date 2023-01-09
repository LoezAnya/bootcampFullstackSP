package com.reto.backend.security.service;

import com.reto.backend.security.entity.Role;
import com.reto.backend.security.enums.ERole;
import com.reto.backend.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> getByERole(ERole eRole){
        return this.roleRepository.findByNamerole(eRole);
    }
}
