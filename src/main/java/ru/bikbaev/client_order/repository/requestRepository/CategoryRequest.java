package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.Category;
import ru.bikbaev.client_order.repository.JPA.JPACategory;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRequest {
    private final JPACategory jpaCategory;

    public CategoryRequest(JPACategory jpaCategory) {
        this.jpaCategory = jpaCategory;
    }

    public Optional<Category> findById(int id){
        return jpaCategory.findById(id);
    }

    public List<Category> getAll(){
        return jpaCategory.findAll();
    }

    public Category creatNewCategory(Category category){
        return jpaCategory.save(category);
    }

    public void deleteCategory(Category category){
        jpaCategory.delete(category);
    }
}
