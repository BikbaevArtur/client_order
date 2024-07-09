package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.TypeAccount;
import ru.bikbaev.client_order.repository.JPA.JPATypeAccount;

import java.util.List;

@Repository
public class TypeAccountRequest {
    private final JPATypeAccount jpaTypeAccount;

    public TypeAccountRequest(JPATypeAccount jpaTypeAccount) {
        this.jpaTypeAccount = jpaTypeAccount;
    }

    public List<TypeAccount> getAll(){
        return jpaTypeAccount.findAll();
    }

    public TypeAccount findById(int id){
        return jpaTypeAccount.findById(id).orElseThrow();
    }

}
