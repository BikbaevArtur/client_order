package ru.bikbaev.client_order.controller.thymeleaf.client_controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.CompanyDTO;
import ru.bikbaev.client_order.model.entity.User;
import ru.bikbaev.client_order.service.admin.CompanyService;
import ru.bikbaev.client_order.service.admin.UserService;

import java.util.List;

@Controller
public class HomeController {

    private final CompanyService companyService;
    private final UserService userService;


    public HomeController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }


    @GetMapping("/home")

    public String homePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        List<CompanyDTO> companyDTOS = companyService.findAllCompanyByUserId(user.getId());
        model.addAttribute("companies", companyDTOS);
        return "client_page/home";
    }


}
