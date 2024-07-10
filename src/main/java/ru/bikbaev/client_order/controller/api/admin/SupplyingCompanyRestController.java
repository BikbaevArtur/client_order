package ru.bikbaev.client_order.controller.api.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.model.entity.SupplyingCompany;
import ru.bikbaev.client_order.service.admin.SupplyingCompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/supplying-company")
public class SupplyingCompanyRestController {
    public final SupplyingCompanyService supplyingCompanyService;

    public SupplyingCompanyRestController(SupplyingCompanyService supplyingCompanyService) {
        this.supplyingCompanyService = supplyingCompanyService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<SupplyingCompany> findByIdSupplyingCompany(@PathVariable int  id){
        SupplyingCompany supplyingCompany = supplyingCompanyService.findById(id);
        return new ResponseEntity<>(supplyingCompany, HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<List<SupplyingCompany>> findAll(){
        return new ResponseEntity<>(supplyingCompanyService.findAll(),HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<SupplyingCompany> creatSupplyingCompany(@RequestBody SupplyingCompany supplyingCompany){
        return new ResponseEntity<>(supplyingCompanyService.creatNewCompany(supplyingCompany),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")

    public ResponseEntity<SupplyingCompany> deleteSupplyingCompany(@PathVariable int id){
        supplyingCompanyService.deleteSupplyingCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
