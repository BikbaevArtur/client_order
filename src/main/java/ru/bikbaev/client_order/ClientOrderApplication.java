package ru.bikbaev.client_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@SpringBootApplication
public class ClientOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientOrderApplication.class, args);
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll()
//                )
//                .formLogin(login -> login
//                        .loginPage("/").permitAll()
//                        .defaultSuccessUrl("/home", true)
//                )
//                .csrf(AbstractHttpConfigurer::disable)
//                .build();
//    }

}
