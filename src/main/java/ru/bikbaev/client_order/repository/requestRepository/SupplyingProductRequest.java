package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.SupplyingProduct;
import ru.bikbaev.client_order.repository.JPA.JPASupplyingProduct;

import java.util.List;

@Repository
public class SupplyingProductRequest {
    private final JPASupplyingProduct jpaSupplyingProduct;

    public SupplyingProductRequest(JPASupplyingProduct jpaSupplyingProduct) {
        this.jpaSupplyingProduct = jpaSupplyingProduct;
    }


    public void deleteSupplyingProduct(SupplyingProduct supplyingProduct) {
        jpaSupplyingProduct.delete(supplyingProduct);
    }


    public List<SupplyingProduct> findBySupplyingProductId_SupplyingId(int supplyingId) {
        return jpaSupplyingProduct.findBySupplyingProductId_SupplyingId(supplyingId);
    }

}
