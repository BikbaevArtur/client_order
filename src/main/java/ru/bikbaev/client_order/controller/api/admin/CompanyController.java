package ru.bikbaev.client_order.controller.api.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.model.dto.CompanyDTO;
import ru.bikbaev.client_order.service.admin.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<CompanyDTO> creatNewCompany(@RequestBody CompanyDTO companyDTO) {
        return new ResponseEntity<>(service.creatNewCompany(companyDTO), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<CompanyDTO>> findAllCompany(){
        return new ResponseEntity<>(service.findAllCompany(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> findCompanyById(@PathVariable int id){
        return new ResponseEntity<>(service.findById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable int id){
        service.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
