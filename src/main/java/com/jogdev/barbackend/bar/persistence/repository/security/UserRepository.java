package com.jogdev.barbackend.bar.persistence.repository.security;

import com.jogdev.barbackend.bar.persistence.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
