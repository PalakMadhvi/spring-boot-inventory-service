package com.example.inventory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${app.security.username:admin}")
    private String username;

    @Value("${app.security.password:admin123}")
    private String password;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/health").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> {});
        return http.build();
    }

    @Bean
    InMemoryUserDetailsManager users() {
        UserDetails user = User.withUsername(username)
                .password("{noop}" + password)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
