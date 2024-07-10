package ru.bikbaev.client_order.controller.api.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.model.entity.Category;
import ru.bikbaev.client_order.service.admin.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable int id){
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<List<Category>> findAll(){
        return new ResponseEntity<>(categoryService.findAll(),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Category>  creatCategory(@RequestBody Category category){

        return new ResponseEntity<>(categoryService.creatNewCategory(category),HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
