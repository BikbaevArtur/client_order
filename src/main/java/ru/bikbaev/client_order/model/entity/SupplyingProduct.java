package ru.bikbaev.client_order.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bikbaev.client_order.model.entity.compositeKey.SupplyingProductId;

@Data
@NoArgsConstructor
@Entity
@Table(name = "supplying_product")
public class SupplyingProduct {
    @EmbeddedId
    private SupplyingProductId supplyingProductId;

    @ManyToOne
    @MapsId("supplyingId")
    @JoinColumn(name = "id_supplying", nullable = false)
    private Supplying supplying;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

}
