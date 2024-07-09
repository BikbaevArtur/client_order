package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.SupplyingProduct;
import ru.bikbaev.client_order.model.entity.compositeKey.SupplyingProductId;
import ru.bikbaev.client_order.repository.JPA.JPASupplyingProduct;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplyingProductRequest {
    private final JPASupplyingProduct jpaSupplyingProduct;

    public SupplyingProductRequest(JPASupplyingProduct jpaSupplyingProduct) {
        this.jpaSupplyingProduct = jpaSupplyingProduct;
    }


    public Optional<SupplyingProduct> findById(SupplyingProductId supplyingProductId) {
        return jpaSupplyingProduct.findById(supplyingProductId);
    }

    public List<SupplyingProduct> getAll() {
        return jpaSupplyingProduct.findAll();
    }

    public SupplyingProduct creatNewSupplyingProduct(SupplyingProduct supplyingProduct) {
        return jpaSupplyingProduct.save(supplyingProduct);
    }


    public void deleteSupplyingProduct(SupplyingProduct supplyingProduct){
        jpaSupplyingProduct.delete(supplyingProduct);
    }



    public List<SupplyingProduct> findBySupplyingProductId_SupplyingId(int supplyingId){
        return jpaSupplyingProduct.findBySupplyingProductId_SupplyingId(supplyingId);
    }

}
