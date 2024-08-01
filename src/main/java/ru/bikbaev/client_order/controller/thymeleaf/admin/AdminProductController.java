package ru.bikbaev.client_order.controller.thymeleaf.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.mapped.ProductMapper;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.ProductDTO;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.service.admin.ProductService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public AdminProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }


    @GetMapping("/stock/inventory")
    public String inventoryForm(Model model){
        List<Product> products = productService.findAllOriginProduct();
        model.addAttribute("products",products);
        return "/admin_page/admin-inventory";
    }

    @PostMapping("/stock/inventory")
    public String inventory(@RequestParam Map<String,String> quantity) {

        List<ProductDTO> productDTOList = productMapper.mapperByUpdateProduct(quantity);

        System.out.println(productDTOList);
        return "redirect:/admin/stock";
    }
}
