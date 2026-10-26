package org.example.productservice.config;

import lombok.RequiredArgsConstructor;
import org.example.productservice.security.JwtAuthenticationFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter
            jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
        return https
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth

                                .requestMatchers(
                                        "/auth/register",
                                        "/auth/login",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CommandLineRunner testPassword(
            PasswordEncoder passwordEncoder) {

        return args -> {

            String encoded =
                    passwordEncoder.encode("password");

            System.out.println("encoded::::"+encoded);

            System.out.println(
                    passwordEncoder.matches(
                            "password",
                            encoded));
        };
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration
                .getAuthenticationManager();
    }

}