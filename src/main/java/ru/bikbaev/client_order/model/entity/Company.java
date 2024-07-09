package ru.bikbaev.client_order.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "company")
@NoArgsConstructor
public class Company {
    @Id
    @Column(name = "id_company")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_company", nullable = false)
    private String nameCompany;

    @Column(name = "inn", nullable = false)
    private String inn;

    @Column(name = "payment_account_number")
    private String paymentAccountNumbers;

    @Column(name = "bik_bank")
    private String bikBank;

    @JoinColumn(name = "id_user", nullable = false)
    @ManyToOne
    private User user;
}
