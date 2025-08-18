package com.jogdev.barbackend.bar.persistence.repository.security;

import com.jogdev.barbackend.bar.persistence.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(Role.RoleEnum name);
}
