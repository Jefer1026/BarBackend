package com.jogdev.barbackend.bar.dto.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotNull
    private int roleId;

    @Size(min = 6)
    private String password;

    @Size(min = 6)
    private String repeatPassword;
}
