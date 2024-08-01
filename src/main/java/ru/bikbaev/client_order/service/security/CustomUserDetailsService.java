package ru.bikbaev.client_order.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bikbaev.client_order.model.entity.Login;
import ru.bikbaev.client_order.model.entity.RoleUser;
import ru.bikbaev.client_order.repository.requestRepository.LoginRequest;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final LoginRequest loginRequest;

    public CustomUserDetailsService(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Login login = loginRequest.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Login not found" + email));
        RoleUser roleUser = login.getRole();
        GrantedAuthority grantedAuthority = convertRoleToAuthority(roleUser);

        return new User(login.getEmail(), login.getPassword(), true, true, true, true, Collections.singleton(grantedAuthority));

    }

    private GrantedAuthority convertRoleToAuthority(RoleUser role) {
        return new SimpleGrantedAuthority(role.getRoleName());
    }


}
