package ru.bikbaev.client_order.repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.client_order.model.entity.StatusOrder;

public interface JPAStatusOrder extends JpaRepository<StatusOrder,Integer> {
}
