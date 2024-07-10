package ru.bikbaev.client_order.model.dto.dtoAdminPanel.orderDTO;

import lombok.Data;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.SupplyingAndOrderProductDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
public class SaleOrderDTO {
    private int id;
    private int companyId;
    private LocalDate dataOrder;
    private BigDecimal amountOrder;
    private int statusOrderID;
    private String checkLink;
    private List<SupplyingAndOrderProductDTO> productDTOS;


}
