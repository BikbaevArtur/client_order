package ru.bikbaev.client_order.controller.api.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.supplyingDTO.SupplyingDTO;
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


    @GetMapping()

    public ResponseEntity<List<SupplyingDTO>> findAll(){
        return  new ResponseEntity<>(supplyingService.findAllSupplying(),HttpStatus.OK);
    }

    @GetMapping("/{id}")

    public ResponseEntity<SupplyingDTO> findSupplyingByIdSupplying(@PathVariable int id){
        return new ResponseEntity<>(supplyingService.findById(id),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<SupplyingDTO> creatNewSupplying(@RequestBody SupplyingRequestDTO supplyingRequestDTO){
        SupplyingDTO supplyingCreatDTO = supplyingRequestDTO.supplyingCreatDTO();
        List<SupplyingAndOrderProductDTO> supplyingProductDTOS = supplyingRequestDTO.supplyingProductDTOS();
      return   new ResponseEntity<>(supplyingService.creatNewSupplying(supplyingCreatDTO,supplyingProductDTOS), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Supplying> deleteSupplying(@PathVariable int id){
        supplyingService.deleteSupplying(id);
      return   new ResponseEntity<>(HttpStatus.OK);
    }


}
