package ru.bikbaev.client_order.repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.client_order.model.entity.User;

import java.util.Optional;

public interface JPAUser extends JpaRepository<User,Integer> {

    Optional<User> findUserByEmail(String email);
}
