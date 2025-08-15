package com.jogdev.barbackend.bar.service;

import com.jogdev.barbackend.bar.dto.auth.UserDto;
import com.jogdev.barbackend.bar.persistence.entity.security.User;

public interface UserService {
    User registerUser(UserDto userDto);
}
