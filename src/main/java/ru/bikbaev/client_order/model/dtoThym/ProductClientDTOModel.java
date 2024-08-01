package ru.bikbaev.client_order.model.dtoThym;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bikbaev.client_order.model.entity.Category;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductClientDTOModel {
    private int id;
    private String nameProduct;
    private int stockBalance;
    private BigDecimal retailSalePrice;
    private BigDecimal internetSalePrice;
    private BigDecimal wholesaleSalePrice;
    private Category category;
    private int quantity;
}
