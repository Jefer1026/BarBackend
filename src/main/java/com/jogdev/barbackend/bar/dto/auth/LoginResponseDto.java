package com.jogdev.barbackend.bar.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private UserResponseDto userResponseDto;
    private String token;
}
