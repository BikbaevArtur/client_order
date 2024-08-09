package ru.bikbaev.client_order.controller.thymeleaf.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.mapped.ProductMapper;
import ru.bikbaev.client_order.model.entity.Category;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.model.entity.SupplyingCompany;
import ru.bikbaev.client_order.service.admin.CategoryService;
import ru.bikbaev.client_order.service.admin.ProductService;
import ru.bikbaev.client_order.service.admin.SupplyingCompanyService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SupplyingCompanyService supplyingCompanyService;
    private final CategoryService categoryService;


    public AdminProductController(ProductService productService, ProductMapper productMapper, SupplyingCompanyService supplyingCompanyService, CategoryService categoryService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.supplyingCompanyService = supplyingCompanyService;
        this.categoryService = categoryService;
    }


    @GetMapping("/stock/inventory")
    public String inventoryForm(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products",products);
        return "/admin_page/admin-inventory";
    }

    @PostMapping("/stock/inventory")
    public String inventory(@RequestParam Map<String,String> quantity) {

        List<Product> product = productMapper.mapperByUpdateProduct(quantity);
        productService.saveAllProduct(product);

        return "redirect:/admin/stock";
    }

    @GetMapping("/product/update/{id}")
    public String updateProductForm(@PathVariable int id,Model model){
        Product product = productService.findById(id);
        List<SupplyingCompany> supplyingCompanies = supplyingCompanyService.findAll();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("product",product);
        model.addAttribute("categories",categories);
        model.addAttribute("supplyingCompanies",supplyingCompanies);

        return "/admin_page/admin-product-update";
    }

    @PostMapping("/product/update/{id}")
    public String updateProduct(Product product){
        System.out.println(product);
        productService.creatNewProduct(product);
        return "redirect:/admin/stock";
    }


    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable int id ){
        productService.deleteProduct(id);
        return "redirect:/admin/stock";
    }
}
