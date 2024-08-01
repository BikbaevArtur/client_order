package ru.bikbaev.client_order.repository.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bikbaev.client_order.model.entity.Company;

import java.util.List;
import java.util.Optional;

public interface JPACompany extends JpaRepository<Company,Integer> {


    Optional<List<Company>> findAllByUserId(int id);

}
