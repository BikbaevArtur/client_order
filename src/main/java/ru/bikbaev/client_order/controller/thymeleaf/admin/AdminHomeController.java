package ru.bikbaev.client_order.controller.thymeleaf.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.model.entity.SaleOrder;
import ru.bikbaev.client_order.service.admin.ProductService;
import ru.bikbaev.client_order.service.admin.SaleOrderService;

import java.util.List;

@Controller()
@RequestMapping("/admin")

public class AdminHomeController {

    private final SaleOrderService saleOrderService;
    private final ProductService productService;

    public AdminHomeController(SaleOrderService saleOrderService, ProductService productService) {
        this.saleOrderService = saleOrderService;
        this.productService = productService;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        List<Product> product = productService.findAll();
        List<SaleOrder> saleOrders = saleOrderService.findAllOriginSaleOrder();

        List<Product> productMinStockBalance = productService.filterByMinBalance(product);
        List<SaleOrder> newSaleOrder = saleOrderService.filterNewSaleOrder(saleOrders);

        productMinStockBalance = productMinStockBalance
                .subList(0,Math.min(productMinStockBalance.size(),5));
        newSaleOrder = newSaleOrder
                .subList(0,Math.min(newSaleOrder.size(),5));


        model.addAttribute("products", productMinStockBalance);
        model.addAttribute("orders", newSaleOrder);


        return "admin_page/admin-home";
    }
}
