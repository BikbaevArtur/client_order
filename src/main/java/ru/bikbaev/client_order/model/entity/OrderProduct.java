package ru.bikbaev.client_order.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bikbaev.client_order.model.entity.compositeKey.OrderProductId;

@Entity
@Table(name = "order_products")
@Data
@NoArgsConstructor
public class OrderProduct {
    @EmbeddedId
    private OrderProductId orderProductId;

    @ManyToOne
    @MapsId("saleOrderId")
    @JoinColumn(name = "id_sale_order", nullable = false)
    private SaleOrder saleOrder;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
