package ru.bikbaev.client_order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.bikbaev.client_order.service.security.CustomUserDetailsService;


@EnableWebSecurity()
@EnableMethodSecurity
@Configuration
public class ConfigurationSecurity {


    private final CustomUserDetailsService customUserDetailsService;

    public ConfigurationSecurity(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers("/api/***").hasRole("ADMIN")
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/home").authenticated()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/").permitAll()
                        .defaultSuccessUrl("/home")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }


}
