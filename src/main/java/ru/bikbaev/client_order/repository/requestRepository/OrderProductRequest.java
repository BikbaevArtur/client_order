package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.OrderProduct;
import ru.bikbaev.client_order.repository.JPA.JPAOrderProduct;

import java.util.List;

@Repository
public class OrderProductRequest {
    private final JPAOrderProduct jpaOrderProduct;

    public OrderProductRequest(JPAOrderProduct jpaOrderProduct) {
        this.jpaOrderProduct = jpaOrderProduct;
    }


    public void deleteOrderProduct(OrderProduct orderProduct) {
        jpaOrderProduct.delete(orderProduct);
    }

    public List<OrderProduct> findByOrderProductId_SaleOrderId(int id) {
        return jpaOrderProduct.findByOrderProductId_SaleOrderId(id);
    }
}
