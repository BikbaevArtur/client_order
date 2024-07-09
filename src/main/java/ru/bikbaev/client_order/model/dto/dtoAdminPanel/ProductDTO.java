package ru.bikbaev.client_order.model.dto.dtoAdminPanel;

import lombok.Data;
import ru.bikbaev.client_order.model.entity.Category;

import java.math.BigDecimal;
@Data
public class ProductDTO {
    private int id;
    private String nameProduct;
    private int stockBalance;
    private int minimumStockBalance;
    private BigDecimal retailSalePrice;
    private BigDecimal internetSalePrice;
    private BigDecimal wholesaleSalePrice;
    private BigDecimal purchasePrice;
    private int supplyingCompanyId;
    private int categoryId;
}
