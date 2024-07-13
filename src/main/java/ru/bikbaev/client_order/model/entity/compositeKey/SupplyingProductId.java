package ru.bikbaev.client_order.model.entity.compositeKey;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Data
@Embeddable
@AllArgsConstructor
public class SupplyingProductId implements Serializable {

    private int supplyingId;
    private int productId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplyingProductId that = (SupplyingProductId) o;
        return supplyingId == that.supplyingId &&
                productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplyingId, productId);
    }


}
