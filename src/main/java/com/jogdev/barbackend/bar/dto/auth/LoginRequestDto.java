package com.jogdev.barbackend.bar.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {


    @NotBlank
    private String username;

    @Size(min = 6, message = "Password required min 6 characters")
    private String password;

}
