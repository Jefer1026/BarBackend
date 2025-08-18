package com.jogdev.barbackend.bar.controller;

import com.jogdev.barbackend.bar.dto.auth.LoginResponseDto;
import com.jogdev.barbackend.bar.dto.auth.UserDto;
import com.jogdev.barbackend.bar.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;


    @PostMapping
    public ResponseEntity<LoginResponseDto> registerUser(@RequestBody @Valid UserDto userDto) {

        LoginResponseDto registeredUserDto = authenticationService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserDto);

    }
}
