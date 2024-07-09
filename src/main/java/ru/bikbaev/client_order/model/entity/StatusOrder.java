package ru.bikbaev.client_order.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "status_order")
@Data
@NoArgsConstructor
public class StatusOrder {
    @Id
    @Column(name = "id_status_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status", nullable = false)
    private String status;
}
