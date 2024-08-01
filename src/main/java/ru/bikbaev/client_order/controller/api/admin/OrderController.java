package ru.bikbaev.client_order.controller.api.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.orderDTO.InvoiceForConfirmationOrderDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.orderDTO.OrderRequestDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.orderDTO.SaleOrderDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.orderDTO.SaleOrderUpdateStatusDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.SupplyingAndOrderProductDTO;
import ru.bikbaev.client_order.model.entity.SaleOrder;
import ru.bikbaev.client_order.service.admin.SaleOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final SaleOrderService service;

    public OrderController(SaleOrderService service) {
        this.service = service;
    }


    @GetMapping()
    public ResponseEntity<List<SaleOrderDTO>> findAllOrder(){
        return new ResponseEntity<>(service.findAllOrder(),HttpStatus.OK);
    }

    @PostMapping("/update-status")
    public ResponseEntity<SaleOrderDTO> updateStatusOrder(@RequestBody SaleOrderUpdateStatusDTO saleOrderUpdateStatusDTO){

        return new ResponseEntity<>(service.updateOrderStatus
                (saleOrderUpdateStatusDTO.getSaleOrderId(),
                saleOrderUpdateStatusDTO.getStatusOrderId()),
                HttpStatus.OK);
    }


    @PostMapping("/invoice")

    public ResponseEntity<SaleOrderDTO> addInvoiceOrder(@RequestBody InvoiceForConfirmationOrderDTO invoice){
        return new ResponseEntity<>(service.invoiceForConfirmationOrder(invoice),HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SaleOrderDTO> findById(@PathVariable int id){
        return new ResponseEntity<>(service.findOrderById(id),HttpStatus.OK);
    }

    @PostMapping()

    public ResponseEntity<SaleOrderDTO> creatNewOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        SaleOrderDTO saleOrderDTO = orderRequestDTO.getSaleOrderDTO();
        List<SupplyingAndOrderProductDTO> supplyingAndOrderProductDTOList = orderRequestDTO.getOrderProductDTOS();
        return new ResponseEntity<>(service.creatNewSaleOrder(saleOrderDTO,supplyingAndOrderProductDTOList), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SaleOrder> deleteSaleOrder(@PathVariable int id){
        service.deleteSaleOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
