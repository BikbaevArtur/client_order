package ru.bikbaev.client_order.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "supplying_company")
@NoArgsConstructor
public class SupplyingCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supplying_company")
    private int id;

    @Column(name = "name_supplying_company", nullable = false)
    private String nameSupplyingCompany;

    @Column(name = "inn_supplying_company", nullable = false)
    private String innSupplyingCompany;
}
