package ru.bikbaev.client_order.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class RoleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private int id;

    @Column(nullable = false, name = "role_name", unique = true)
    private String roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<Login> logins;

}
