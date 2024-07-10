package ru.bikbaev.client_order.model.dto.dtoAdminPanel.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.SupplyingAndOrderProductDTO;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private SaleOrderDTO saleOrderDTO;
    private List<SupplyingAndOrderProductDTO> orderProductDTOS;
}
