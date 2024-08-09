package ru.bikbaev.client_order.controller.thymeleaf.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bikbaev.client_order.model.entity.SaleOrder;
import ru.bikbaev.client_order.service.admin.SaleOrderService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminClientOrderController {
    private final SaleOrderService saleOrderService;

    public AdminClientOrderController(SaleOrderService saleOrderService) {
        this.saleOrderService = saleOrderService;
    }


    @GetMapping("/sale-order")
    public String saleOrder(Model model){
        List<SaleOrder> saleOrders = saleOrderService.findAllOriginSaleOrder();
        model.addAttribute("orders",saleOrders);
        return "/admin_page/admin-client-orders";
    }
}
