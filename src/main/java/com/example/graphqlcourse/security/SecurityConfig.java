package com.example.graphqlcourse.security;

import com.example.graphqlcourse.datasource.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final UserRepository userzRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var authProvider = new CustomAuthenticationProvider(userzRepository);

        http.apply(HttpConfigurer.newInstance());
        http.authenticationProvider(authProvider);
        http.csrf().disable().authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );

        return http.build();
    }
}

