package ru.bikbaev.client_order.controller.thymeleaf.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bikbaev.client_order.model.entity.SupplyingCompany;
import ru.bikbaev.client_order.service.admin.SupplyingCompanyService;

@Controller
@RequestMapping("/admin/supplying-orders/supplying-company")
public class AdminSupplyingCompany {
    private final SupplyingCompanyService supplyingCompanyService;

    public AdminSupplyingCompany(SupplyingCompanyService supplyingCompanyService) {
        this.supplyingCompanyService = supplyingCompanyService;
    }



    @GetMapping("/creat")
    public String creatForm(Model model){
        model.addAttribute("company",new SupplyingCompany());
        return "/admin_page/admin-creat-new-supplying-company";
    }

    @PostMapping("/creat")
    public String creat(SupplyingCompany supplyingCompany){
        supplyingCompanyService.creatNewCompany(supplyingCompany);
        return "redirect:/admin/supplying-orders/supplying-company";
    }


}
