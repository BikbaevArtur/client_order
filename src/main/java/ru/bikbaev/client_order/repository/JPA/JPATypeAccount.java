package ru.bikbaev.client_order.repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.client_order.model.entity.TypeAccount;

public interface JPATypeAccount extends JpaRepository<TypeAccount,Integer> {
}
