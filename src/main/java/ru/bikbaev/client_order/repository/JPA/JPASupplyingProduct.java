package ru.bikbaev.client_order.repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.client_order.model.entity.SupplyingProduct;
import ru.bikbaev.client_order.model.entity.compositeKey.SupplyingProductId;

import java.util.List;

public interface JPASupplyingProduct extends JpaRepository<SupplyingProduct, SupplyingProductId> {

    List<SupplyingProduct> findBySupplyingProductId_SupplyingId(int supplyingId);


}
