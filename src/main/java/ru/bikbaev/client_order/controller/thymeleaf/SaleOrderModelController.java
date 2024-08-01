package ru.bikbaev.client_order.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.CompanyDTO;
import ru.bikbaev.client_order.model.entity.Company;
import ru.bikbaev.client_order.model.entity.SaleOrder;
import ru.bikbaev.client_order.service.admin.CompanyService;
import ru.bikbaev.client_order.service.admin.SaleOrderService;

import java.util.List;

@Controller

public class SaleOrderModelController {

    private final SaleOrderService service;
    private final CompanyService companyService;

    public SaleOrderModelController(SaleOrderService service, CompanyService companyService) {
        this.service = service;
        this.companyService = companyService;
    }

    @GetMapping("/company/{id}")
    public String allOrders( Model model, @PathVariable int id){
        CompanyDTO company = companyService.findById(id);

        List<SaleOrder> saleOrderList = service.findSaleOrderByCompanyId(company.getId());

        model.addAttribute("orders",saleOrderList);
        return "sale-order";
    }
}
