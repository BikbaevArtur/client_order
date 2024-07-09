package ru.bikbaev.client_order.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "sale_order")
@NoArgsConstructor
public class SaleOrder {
    @Id
    @Column(name = "id_sale_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "id_company", nullable = false)
    @ManyToOne
    private Company company;

    @Column(name = "data_order", nullable = false)
    private LocalDate dataOrder;

    @Column(name = "amount_order", nullable = false)
    private BigDecimal amountOrder;

    @JoinColumn(name = "id_status_order", nullable = false)
    @ManyToOne
    private StatusOrder statusOrder;

    @Column(name = "check_link")
    private String checkLink;

    @OneToMany(mappedBy = "saleOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts;
}
