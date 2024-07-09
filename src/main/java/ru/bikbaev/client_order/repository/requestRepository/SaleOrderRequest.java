package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.SaleOrder;
import ru.bikbaev.client_order.repository.JPA.JPASaleOrder;

import java.util.List;
import java.util.Optional;

@Repository
public class SaleOrderRequest {
    private  final JPASaleOrder jpaSaleOrder;

    public SaleOrderRequest(JPASaleOrder jpaSaleOrder) {
        this.jpaSaleOrder = jpaSaleOrder;
    }

    public Optional<SaleOrder> findById(int id){
        return jpaSaleOrder.findById(id);
    }

    public List<SaleOrder> getAll(){
        return jpaSaleOrder.findAll();
    }

    public SaleOrder creatNewSaleOrder(SaleOrder saleOrder){
        return jpaSaleOrder.save(saleOrder);
    }

    public void deleteSaleOrder(SaleOrder saleOrder){
        jpaSaleOrder.delete(saleOrder);
    }


}
