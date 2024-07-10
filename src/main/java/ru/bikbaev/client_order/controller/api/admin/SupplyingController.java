package ru.bikbaev.client_order.controller.api.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.supplyingDTO.SupplyingCreatDTO;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.supplyingDTO.SupplyingFindDTO;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.SupplyingAndOrderProductDTO;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.supplyingDTO.SupplyingRequestDTO;
import ru.bikbaev.client_order.model.entity.Supplying;
import ru.bikbaev.client_order.service.admin.SupplyingService;

import java.util.List;

@RestController
@RequestMapping("/api/supplying")
public class SupplyingController {
    private final SupplyingService supplyingService;

    public SupplyingController(SupplyingService supplyingService) {
        this.supplyingService = supplyingService;
    }


    @PostMapping()
    public ResponseEntity<SupplyingCreatDTO> creatNewSupplying(@RequestBody SupplyingRequestDTO supplyingRequestDTO){
        SupplyingCreatDTO supplyingCreatDTO = supplyingRequestDTO.supplyingCreatDTO();
        List<SupplyingAndOrderProductDTO> supplyingProductDTOS = supplyingRequestDTO.supplyingProductDTOS();
      return   new ResponseEntity<>(supplyingService.creatNewSupplying(supplyingCreatDTO,supplyingProductDTOS), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")

    public ResponseEntity<SupplyingFindDTO> findSupplyingByIdSupplying(@PathVariable int id){
        return new ResponseEntity<>(supplyingService.findById(id),HttpStatus.OK);
    }


    @GetMapping()

    public ResponseEntity<List<SupplyingFindDTO>> findAll(){
        return  new ResponseEntity<>(supplyingService.findAll(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Supplying> deleteSupplying(@PathVariable int id){
        supplyingService.deleteSupplying(id);
      return   new ResponseEntity<>(HttpStatus.OK);
    }


}
