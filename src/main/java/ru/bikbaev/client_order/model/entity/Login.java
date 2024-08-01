package ru.bikbaev.client_order.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id_user")
    private User user;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private RoleUser role;


    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Login login = (Login) obj;
        return id == login.id &&
                Objects.equals(email, login.email);
    }
}
