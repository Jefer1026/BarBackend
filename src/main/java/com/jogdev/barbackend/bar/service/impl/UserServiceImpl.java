package com.jogdev.barbackend.bar.service.impl;

import com.jogdev.barbackend.bar.dto.auth.UserDto;
import com.jogdev.barbackend.bar.exception.InvalidPasswordException;
import com.jogdev.barbackend.bar.exception.ObjectNotFoundException;
import com.jogdev.barbackend.bar.persistence.entity.security.Role;
import com.jogdev.barbackend.bar.persistence.entity.security.User;
import com.jogdev.barbackend.bar.persistence.repository.security.RoleRepository;
import com.jogdev.barbackend.bar.persistence.repository.security.UserRepository;
import com.jogdev.barbackend.bar.service.RoleService;
import com.jogdev.barbackend.bar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;


    @Override
    public User registerUser(UserDto userDto) {
        validation(userDto);

        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(()-> new ObjectNotFoundException("Role Not Found"));

        user.setRole(role);

        return userRepository.save(user);
    }


    private void validation(UserDto userDto) {
        if (!StringUtils.hasText(userDto.getPassword()) || !StringUtils.hasText(userDto.getRepeatPassword())) {
            throw new InvalidPasswordException("Password or Repeat-Password Required");
        }
        if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
            throw new InvalidPasswordException("Passwords do not match");
        }
    }
}