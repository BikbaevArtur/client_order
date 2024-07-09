package ru.bikbaev.client_order.model.dto.dtoAdminPanel.supplyingDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bikbaev.client_order.model.entity.SupplyingProduct;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class SupplyingCreatDTO {
    private int id;
    private int supplyingCompanyID;
    private LocalDate deliveryDate;
    private BigDecimal deliveryAmount;
    private List<SupplyingProduct> supplyingProducts;
}
