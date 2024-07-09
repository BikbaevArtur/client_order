package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.StatusOrder;
import ru.bikbaev.client_order.repository.JPA.JPAStatusOrder;

import java.util.List;
import java.util.Optional;

@Repository
public class StatusOrderRequest {
    private final JPAStatusOrder jpaStatusOrder;

    public StatusOrderRequest(JPAStatusOrder jpaStatusOrder) {
        this.jpaStatusOrder = jpaStatusOrder;
    }

    public Optional<StatusOrder> findById(int id){
        return jpaStatusOrder.findById(id);
    }

    public List<StatusOrder> getAll(){
        return jpaStatusOrder.findAll();
    }
}
