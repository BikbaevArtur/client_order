package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.Company;
import ru.bikbaev.client_order.repository.JPA.JPACompany;

import java.util.List;
import java.util.Optional;

@Repository
public class CompanyRequest {
    private final JPACompany jpaCompany;

    public CompanyRequest(JPACompany jpaCompany) {
        this.jpaCompany = jpaCompany;
    }

    public Optional<Company> findById(int id){
        return jpaCompany.findById(id);
    }

    public List<Company> getAll(){
        return jpaCompany.findAll();
    }

    public Company creatNewCompany(Company company){
        return jpaCompany.save(company);
    }

    public void deleteCompany(Company company){
        jpaCompany.delete(company);
    }
}
