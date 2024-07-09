package ru.bikbaev.client_order.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_product", nullable = false)
    private String nameProduct;

    @Column(name = "stock_balance", nullable = false)
    private int stockBalance;

    @Column(name = "minimum_stock_balance", nullable = false)
    private int minimumStockBalance;

    @Column(name = "retail_sale_price")
    private BigDecimal retailSalePrice;

    @Column(name = "internet_sale_price")
    private BigDecimal internetSalePrice;

    @Column(name = "wholesale_sale_price", nullable = false)
    private BigDecimal wholesaleSalePrice;

    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice;

    @JoinColumn(name = "id_supplying_company", nullable = false)
    @ManyToOne
    private SupplyingCompany supplyingCompany;

    @JoinColumn(name = "id_category", nullable = false)
    @ManyToOne
    private Category category;
}
