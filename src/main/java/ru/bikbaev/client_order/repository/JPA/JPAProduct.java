package ru.bikbaev.client_order.repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.client_order.model.entity.Product;

public interface JPAProduct extends JpaRepository<Product,Integer> {
}
