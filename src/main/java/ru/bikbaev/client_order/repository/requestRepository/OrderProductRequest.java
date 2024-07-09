package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.OrderProduct;
import ru.bikbaev.client_order.model.entity.compositeKey.OrderProductId;
import ru.bikbaev.client_order.repository.JPA.JPAOrderProduct;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderProductRequest {
    private final JPAOrderProduct jpaOrderProduct;

    public OrderProductRequest(JPAOrderProduct jpaOrderProduct) {
        this.jpaOrderProduct = jpaOrderProduct;
    }

    public Optional<OrderProduct> findById(OrderProductId orderProductId){
        return jpaOrderProduct.findById(orderProductId);
    }

    public List<OrderProduct> getAll(){
        return jpaOrderProduct.findAll();
    }

    public OrderProduct creatNewOrderProduct(OrderProduct orderProduct){
        return jpaOrderProduct.save(orderProduct);
    }

    public void deleteOrderProduct(OrderProduct orderProduct){
        jpaOrderProduct.delete(orderProduct);
    }

    public List<OrderProduct> findByOrderProductId_SaleOrderId(int id){
        return jpaOrderProduct.findByOrderProductId_SaleOrderId(id);
    }
}
