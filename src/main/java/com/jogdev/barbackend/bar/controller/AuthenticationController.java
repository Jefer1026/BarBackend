package com.jogdev.barbackend.bar.controller;

import com.jogdev.barbackend.bar.dto.auth.LoginRequestDto;
import com.jogdev.barbackend.bar.dto.auth.LoginResponseDto;
import com.jogdev.barbackend.bar.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponseDto> authenticate(
            @RequestBody @Valid LoginRequestDto loginRequestDto) {

        return ResponseEntity.ok(authenticationService.authenticate(loginRequestDto));

    }


    @GetMapping("/check-status")
    public ResponseEntity<LoginResponseDto> checkStatus(
            @RequestParam(required = true) String token
    ) {

        return ResponseEntity.ok(authenticationService.chekStatus(token));

    }
}
