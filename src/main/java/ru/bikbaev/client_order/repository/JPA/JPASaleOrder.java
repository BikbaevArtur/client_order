package ru.bikbaev.client_order.repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.client_order.model.entity.SaleOrder;

import java.util.List;
import java.util.Optional;

public interface JPASaleOrder extends JpaRepository<SaleOrder,Integer> {

    Optional<List<SaleOrder>> findSaleOrderByCompanyId(int id);
}
