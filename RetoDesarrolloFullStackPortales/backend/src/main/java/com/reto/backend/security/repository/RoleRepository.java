package com.reto.backend.security.repository;

import com.reto.backend.security.entity.Role;
import com.reto.backend.security.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByNamerole(ERole nameRole);
}
