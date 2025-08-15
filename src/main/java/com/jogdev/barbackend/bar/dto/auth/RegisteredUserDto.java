package com.jogdev.barbackend.bar.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredUserDto {
    private int id;
    private String name;
    private String username;
    private String role;
    private String token;

}
