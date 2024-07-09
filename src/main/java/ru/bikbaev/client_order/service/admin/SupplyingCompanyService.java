package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
import ru.bikbaev.client_order.model.entity.SupplyingCompany;
import ru.bikbaev.client_order.repository.requestRepository.SupplyingCompanyRequest;

import java.util.List;

@Service
public class SupplyingCompanyService {
    private final SupplyingCompanyRequest supplyingCompanyRequest;

    public SupplyingCompanyService(SupplyingCompanyRequest supplyingCompanyRequest) {
        this.supplyingCompanyRequest = supplyingCompanyRequest;
    }


    public SupplyingCompany creatNewCompany(SupplyingCompany supplyingCompany) {
        return supplyingCompanyRequest.creatNewSupplyingCompany(supplyingCompany);
    }

    public SupplyingCompany findById(int id) {
        return supplyingCompanyRequest.findById(id).orElseThrow();
    }

    public List<SupplyingCompany> findAll() {
        return supplyingCompanyRequest.getAll();
    }

    public void deleteSupplyingCompany(int id) {
        supplyingCompanyRequest.deleteSupplyingCompany(findById(id));
    }


}
