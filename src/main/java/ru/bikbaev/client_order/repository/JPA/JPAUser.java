package ru.bikbaev.client_order.repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.client_order.model.entity.User;

public interface JPAUser extends JpaRepository<User,Integer> {
}
