//package ru.bikbaev.client_order.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import ru.bikbaev.client_order.service.admin.CompanyService;
//import ru.bikbaev.client_order.service.admin.UserService;
//
//import java.io.IOException;
//
//@Component
//public class CompanyAccessFilter extends OncePerRequestFilter {
//    private final UserService userService;
//    private final CompanyService companyService;
//
//    public CompanyAccessFilter(UserService userService, CompanyService companyService) {
//        this.userService = userService;
//        this.companyService = companyService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String path = request.getRequestURI();
//    }
//}
