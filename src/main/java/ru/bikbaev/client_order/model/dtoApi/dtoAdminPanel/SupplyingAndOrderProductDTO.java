package ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyingAndOrderProductDTO {
    private int productID;
    private int quantity;
    private BigDecimal price;
}
