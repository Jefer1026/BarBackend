package com.jogdev.barbackend.config;

import com.jogdev.barbackend.bar.service.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/authentication/**",
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/swagger-resources",
                            "/webjars/**").permitAll();
                    auth.requestMatchers("/user").hasAuthority("user:read-all");
                    auth.requestMatchers("/user/**").hasAuthority("user:write-by-id");
                    auth.requestMatchers("/categories").hasAuthority("categories:read-all");
                    auth.requestMatchers("/categories/**").hasAuthority("categories:read-by-id");
                    auth.requestMatchers("/categories/**").hasAuthority("categories:write-by-id");
                    auth.requestMatchers("/products").hasAuthority("products:read-all");
                    auth.requestMatchers("/products/**").hasAuthority("products:read-by-id");
                    auth.requestMatchers("/products/**").hasAuthority("products:write-by-id");
                    auth.requestMatchers("/sales").hasAuthority("sales:read-all");
                    auth.requestMatchers("/sales/**").hasAuthority("sales:read-by-id");
                    auth.requestMatchers("/sales/**").hasAuthority("write:read-by-id");
                    auth.anyRequest().authenticated();

                })
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(authenticationEntryPoint);
                    exception.accessDeniedHandler(accessDeniedHandler);
                })
                .build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "https://jeferson-portfolio.netlify.app",
                "https://jogdev.com",
                "https://api.jogdev.com",
                "https://rickandmortyjog.netlify.app"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

