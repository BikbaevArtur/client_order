package ru.bikbaev.client_order.repository.JPA;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.client_order.model.entity.Login;

import java.util.Optional;

public interface JPALogin extends JpaRepository<Login,Integer> {

    Optional<Login> findByEmail(String email);

}
