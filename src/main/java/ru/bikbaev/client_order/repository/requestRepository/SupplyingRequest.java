package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.Supplying;
import ru.bikbaev.client_order.repository.JPA.JPASupplying;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplyingRequest {
    private final JPASupplying jpaSupplying;

    public SupplyingRequest(JPASupplying jpaSupplying) {
        this.jpaSupplying = jpaSupplying;
    }


    public Optional<Supplying> findById(int id){
        return jpaSupplying.findById(id);
    }

    public List<Supplying> getAll(){
        return jpaSupplying.findAll();
    }

    public Supplying creatNewSupplying(Supplying supplying){
        return jpaSupplying.save(supplying);
    }


    public void deleteSupplying(Supplying supplying){
        jpaSupplying.delete(supplying);
    }




}
