package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
import ru.bikbaev.client_order.model.entity.Category;
import ru.bikbaev.client_order.repository.requestRepository.CategoryRequest;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRequest categoryRequest;

    public CategoryService(CategoryRequest categoryRequest) {
        this.categoryRequest = categoryRequest;
    }


    public Category findById(int id){
        return categoryRequest.findById(id).orElseThrow();
    }

    public List<Category> findAll(){
        return categoryRequest.getAll();
    }

    public Category creatNewCategory(Category category){
        return categoryRequest.creatNewCategory(category);
    }

    public void deleteCategory(int id){
        Category category = findById(id);
        categoryRequest.deleteCategory(category);
    }
}
