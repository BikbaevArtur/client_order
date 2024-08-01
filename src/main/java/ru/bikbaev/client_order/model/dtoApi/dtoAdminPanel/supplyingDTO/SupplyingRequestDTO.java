package ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.supplyingDTO;

import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.SupplyingAndOrderProductDTO;

import java.util.List;

public record SupplyingRequestDTO(
        SupplyingDTO supplyingCreatDTO,
        List<SupplyingAndOrderProductDTO> supplyingProductDTOS) {
}
