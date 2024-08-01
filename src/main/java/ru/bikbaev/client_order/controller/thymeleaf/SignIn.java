package ru.bikbaev.client_order.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class SignIn {


    @GetMapping()
    public String signIn() {
        return "sign-in";
    }

}
