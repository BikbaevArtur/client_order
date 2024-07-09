package ru.bikbaev.client_order.model.dto.dtoAdminPanel.supplyingDTO;

import ru.bikbaev.client_order.model.dto.dtoAdminPanel.SupplyingAndOrderProductDTO;

import java.util.List;

public record SupplyingRequestDTO(
        SupplyingCreatDTO supplyingCreatDTO,
        List<SupplyingAndOrderProductDTO> supplyingProductDTOS) {
}
