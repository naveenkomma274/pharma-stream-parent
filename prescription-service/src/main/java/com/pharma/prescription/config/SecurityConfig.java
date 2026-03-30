package com.pharma.prescription.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabled for internal R&D testing
                .authorizeHttpRequests(auth -> auth
                        // 1. Keep Swagger and Docs PUBLIC (Fixes your myth!)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/prescriptions/v3/api-docs/**").permitAll()

                        // 2. Only DOCTOR role can create prescriptions
                        .requestMatchers(HttpMethod.POST, "/api/prescriptions/**").hasRole("DOCTOR")

                        // 3. Any other request (like GET) just needs a valid login
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Simple login for now

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Creating in-memory users for Day 10 testing
        UserDetails doctor = User.builder()
                .username("dr-house")
                .password(passwordEncoder().encode("pharma123"))
                .roles("DOCTOR")
                .build();

        return new InMemoryUserDetailsManager(doctor);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
