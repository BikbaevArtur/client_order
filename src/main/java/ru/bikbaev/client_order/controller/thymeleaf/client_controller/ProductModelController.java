package ru.bikbaev.client_order.controller.thymeleaf.client_controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bikbaev.client_order.mapped.ProductMapper;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.ProductDTO;
import ru.bikbaev.client_order.model.dtoThym.ProductClientDTOModel;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.service.admin.ProductService;

import java.util.List;

@Controller
public class ProductModelController {
    private final ProductService productService;
    private final ProductMapper productMapper;


    public ProductModelController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/price")
    public String getPrice(Model model) {
        List<Product> products = productService.findAll();
        List<ProductDTO> productDTOS = productMapper.allProductMappedByProductDTO(products);
        List<ProductClientDTOModel> productModelControllers = productMapper.convertAllProductsDTOToProdClDToMod(productDTOS);
        model.addAttribute("products",productModelControllers);
        return "client_page/price";
    }
}
