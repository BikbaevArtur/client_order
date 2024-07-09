package ru.bikbaev.client_order.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "supplying")
@NoArgsConstructor
public class Supplying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supplying")
    private int id;

    @JoinColumn(name = "id_supplying_company", nullable = false)
    @ManyToOne
    private SupplyingCompany supplyingCompany;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @Column(name = "delivery_amount", nullable = false)
    private BigDecimal deliveryAmount;
    @JsonIgnore
    @OneToMany(mappedBy = "supplying", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplyingProduct> supplyingProducts;
}
