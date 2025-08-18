package com.jogdev.barbackend.bar.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String username;
    private String role;
}
