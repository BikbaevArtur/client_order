package ru.bikbaev.client_order.model.entity.compositeKey;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
@AllArgsConstructor

public class OrderProductId {
    private  int saleOrderId;
    private int productId;
}
