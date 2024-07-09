package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.SupplyingCompany;
import ru.bikbaev.client_order.repository.JPA.JPASupplyingCompany;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplyingCompanyRequest {
    private final JPASupplyingCompany jpaSupplyingCompany;

    public SupplyingCompanyRequest(JPASupplyingCompany jpaSupplyingCompany) {
        this.jpaSupplyingCompany = jpaSupplyingCompany;
    }


    public Optional<SupplyingCompany> findById(int id){
        return jpaSupplyingCompany.findById(id);
    }

    public List<SupplyingCompany> getAll(){
        return jpaSupplyingCompany.findAll();
    }

    public SupplyingCompany creatNewSupplyingCompany(SupplyingCompany supplyingCompany){
        return jpaSupplyingCompany.save(supplyingCompany);
    }

    public void deleteSupplyingCompany(SupplyingCompany supplyingCompany){
        jpaSupplyingCompany.delete(supplyingCompany);
    }


}
