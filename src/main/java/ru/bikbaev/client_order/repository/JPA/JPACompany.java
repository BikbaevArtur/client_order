package ru.bikbaev.client_order.repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.client_order.model.entity.Company;

public interface JPACompany extends JpaRepository<Company,Integer> {
}
