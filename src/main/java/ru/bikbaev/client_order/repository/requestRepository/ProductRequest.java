package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.repository.JPA.JPAProduct;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRequest {
    private final JPAProduct jpaProduct;

    public ProductRequest(JPAProduct jpaProduct) {
        this.jpaProduct = jpaProduct;
    }

    public Optional<Product> findById(int id){
        return jpaProduct.findById(id);
    }

    public List<Product> getAll(){
        return jpaProduct.findAll();
    }

    public Product creatNewProduct(Product product){
        return jpaProduct.save(product);
    }

    public void deleteProduct(int id){
        jpaProduct.deleteById(id);
    }


    public List<Product> saveAll(List<Product>products){
        return jpaProduct.saveAll(products);
    }
}
