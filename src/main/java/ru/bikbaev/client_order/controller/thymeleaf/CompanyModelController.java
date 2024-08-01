package ru.bikbaev.client_order.controller.thymeleaf;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bikbaev.client_order.mapped.CompanyMapper;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.CompanyDTO;
import ru.bikbaev.client_order.model.dtoThym.CompanyCreatDTOModel;
import ru.bikbaev.client_order.model.entity.Company;
import ru.bikbaev.client_order.model.entity.User;
import ru.bikbaev.client_order.service.admin.CompanyService;
import ru.bikbaev.client_order.service.admin.UserService;

@Controller
public class CompanyModelController {
    private final CompanyService companyService;
    private final UserService userService;
    private final CompanyMapper companyMapper;


    public CompanyModelController(CompanyService companyService, UserService userService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.userService = userService;
        this.companyMapper = companyMapper;
    }



    @GetMapping("/company-creat")
    public String creatCompanyGet(Model model){
        model.addAttribute("company", new CompanyCreatDTOModel());
        return "creat-user-company";
    }

    @PostMapping("/company-creat")
    public String creatCompanyPost(@AuthenticationPrincipal UserDetails userDetails
            , CompanyCreatDTOModel companyCreatDTOModel){
        User user = userService.findByEmail(userDetails.getUsername());

        CompanyDTO companyDTO = companyMapper.convertCompanyDToToCompany(companyCreatDTOModel,user);

        companyService.creatNewCompany(companyDTO);
        return "redirect:/home";
    }
}
