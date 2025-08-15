package com.jogdev.barbackend.bar.service.auth;

import com.jogdev.barbackend.bar.dto.auth.LoginRequestDto;
import com.jogdev.barbackend.bar.dto.auth.LoginResponseDto;
import com.jogdev.barbackend.bar.dto.auth.UserDto;
import com.jogdev.barbackend.bar.dto.auth.UserResponseDto;
import com.jogdev.barbackend.bar.persistence.entity.security.User;
import com.jogdev.barbackend.bar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    private Map<String, Object> generateExtraClaim(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        String roleName = ((User) userDetails).getRole().getName().name();

        extraClaims.put("role", roleName);
        extraClaims.put("authorities", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return extraClaims;
    }

    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword());

        System.out.println(loginRequestDto.getUsername());
        System.out.println(loginRequestDto.getPassword());

        Authentication auth = authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails, generateExtraClaim(userDetails));

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(userDetails.getUsername());
        userResponseDto.setRole(((User) userDetails).getRole().getName().name());

        loginResponseDto.setUserResponseDto(userResponseDto);
        loginResponseDto.setToken(token);
        return loginResponseDto;

    }


    public LoginResponseDto chekStatus(String token) {

        LoginResponseDto loginResponseDto = new LoginResponseDto();

        String username = jwtService.extractUsername(token);



        if (username != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);


            System.out.println(userDetails.getUsername());
            if (jwtService.isTokenValid(token, userDetails)) {

                UserResponseDto userResponseDto = new UserResponseDto();
                token = jwtService.generateToken(userDetails, generateExtraClaim(userDetails));
                userResponseDto.setUsername(userDetails.getUsername());
                userResponseDto.setRole(((User) userDetails).getRole().getName().name());


                loginResponseDto.setUserResponseDto(userResponseDto);
                loginResponseDto.setToken(token);


            }
        } else {
            throw new AuthenticationCredentialsNotFoundException("Invalid token");
        }
        return loginResponseDto;

    }

    public UserDetails getUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationCredentialsNotFoundException("Invalid token");


        }
        return (UserDetails) authentication.getPrincipal();
    }

    public LoginResponseDto registerUser(UserDto userDto) {

        User newUser = userService.registerUser(userDto);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(newUser.getUsername());



        userResponseDto.setRole(newUser.getRole().getName().name());

        loginResponseDto.setUserResponseDto(userResponseDto);
        loginResponseDto.setToken(jwtService.generateToken(newUser, generateExtraClaim(newUser)));
        return loginResponseDto;



    }


}
