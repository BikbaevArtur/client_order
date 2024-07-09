package ru.bikbaev.client_order.model.dto.dtoAdminPanel;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDTO {

    private SaleOrderDTO saleOrderDTO;
    private List<SupplyingAndOrderProductDTO> orderProductDTOS;
}
