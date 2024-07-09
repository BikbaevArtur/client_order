package ru.bikbaev.client_order.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type_account")
@Data
@NoArgsConstructor
public class TypeAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_account")
    private int id;

    @Column(name = "type_of_account", nullable = false)
    private String typeOfAccount;

}
