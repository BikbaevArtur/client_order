package ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaleOrderUpdateStatusDTO {
    private int saleOrderId;
    private int statusOrderId;
}
