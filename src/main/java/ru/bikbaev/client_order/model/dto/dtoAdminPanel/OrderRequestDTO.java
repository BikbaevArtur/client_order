package ru.bikbaev.client_order.model.dto.dtoAdminPanel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private SaleOrderDTO saleOrderDTO;
    private List<SupplyingAndOrderProductDTO> orderProductDTOS;
}
