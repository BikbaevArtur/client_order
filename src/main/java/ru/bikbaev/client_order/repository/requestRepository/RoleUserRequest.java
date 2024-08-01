package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bikbaev.client_order.model.entity.RoleUser;
import ru.bikbaev.client_order.repository.JPA.JPARoleUser;

import java.util.Optional;

@Repository
public class RoleUserRequest {
    private final JPARoleUser jpaRoleUser;

    public RoleUserRequest(JPARoleUser jpaRoleUser) {
        this.jpaRoleUser = jpaRoleUser;
    }


    public Optional<RoleUser> findById(int id){
        return jpaRoleUser.findById(id);
    }


}
