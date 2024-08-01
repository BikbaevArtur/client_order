package ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.supplyingDTO;

import lombok.Data;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.SupplyingAndOrderProductDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
public class SupplyingDTO {
    private int id;
    private int supplyingCompanyID;
    private LocalDate deliveryDate;
    private BigDecimal deliveryAmount;
    private List<SupplyingAndOrderProductDTO> productDTOS;
}
