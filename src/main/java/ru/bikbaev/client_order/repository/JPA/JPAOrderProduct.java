package ru.bikbaev.client_order.repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.client_order.model.entity.OrderProduct;
import ru.bikbaev.client_order.model.entity.compositeKey.OrderProductId;

import java.util.List;

public interface JPAOrderProduct extends JpaRepository<OrderProduct, OrderProductId> {
    List<OrderProduct> findByOrderProductId_SaleOrderId(int id);
}
