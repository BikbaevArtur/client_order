package ru.bikbaev.client_order.controller.thymeleaf.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bikbaev.client_order.model.entity.Category;
import ru.bikbaev.client_order.service.admin.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/category")
    public String category(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("newCategory",new Category());
        model.addAttribute("categories",categories);
        return "/admin_page/admin-category";
    }


    @PostMapping("/category")
    public String creatCategory(Category category){

        categoryService.creatNewCategory(category);
        return "redirect:/admin/category";
    }


}
