package ru.bikbaev.client_order.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.mapped.ProductMapper;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.CompanyDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.ProductDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.SupplyingAndOrderProductDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.orderDTO.SaleOrderDTO;
import ru.bikbaev.client_order.model.dtoThym.ProductClientDTOModel;
import ru.bikbaev.client_order.model.entity.SaleOrder;
import ru.bikbaev.client_order.service.admin.CompanyService;
import ru.bikbaev.client_order.service.admin.ProductService;
import ru.bikbaev.client_order.service.admin.SaleOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller

public class SaleOrderModelController {

    private final SaleOrderService service;
    private final CompanyService companyService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    public SaleOrderModelController(SaleOrderService service, CompanyService companyService, ProductService productService, ProductMapper productMapper) {
        this.service = service;
        this.companyService = companyService;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/company/{id}")
    public String allOrders(Model model, @PathVariable int id) {
        CompanyDTO company = companyService.findById(id);

        List<SaleOrder> saleOrderList = service.findSaleOrderByCompanyId(company.getId());

        model.addAttribute("orders", saleOrderList);
        return "sale-order";
    }


    @GetMapping("/company/{id}/create-order")
    public String creatNewOrder(Model model, @PathVariable int id) {
        List<ProductDTO> productDTOS = productService.findAll();
        List<ProductClientDTOModel> productClientDTOModels = productMapper.convertAllProductsDTOToProdClDToMod(productDTOS);

        model.addAttribute("idCompany",id);
        model.addAttribute("products", productClientDTOModels);

        return "creat-company-order";
    }


//    @PostMapping("/company/{id}/create-order")
//    public String creatNewOrder(@PathVariable int id, @ModelAttribute("products") ArrayList<ProductClientDTOModel> order) {
//
//        System.out.println(order);
//        SaleOrderDTO saleOrderDTO = new SaleOrderDTO();
//        saleOrderDTO.setCompanyId(id);
////        service.creatNewSaleOrder(saleOrderDTO, order);
//        return "redirect:/company/"+id;
//    }

    @PostMapping("/company/{id}/create-order")
    public String creatNewOrder(@PathVariable int id,
                                @RequestParam(value = "selectedProducts", required = false) List<Integer> selectedProductIds,
                                @RequestParam Map<String, String> quantities) {

        List<ProductClientDTOModel> order = new ArrayList<>();

        if (selectedProductIds != null) {
            for (Integer productId : selectedProductIds) {
                String quantityStr = quantities.get("quantities[" + productId + "]");

                int quantity = (quantityStr != null && !quantityStr.isEmpty()) ? Integer.parseInt(quantityStr) : 0;

                ProductClientDTOModel product = new ProductClientDTOModel();
                product.setId(productId);
                product.setQuantity(quantity);
                order.add(product);
            }
        }

        SaleOrderDTO saleOrderDTO = new SaleOrderDTO();
        saleOrderDTO.setCompanyId(id);

        service.creatNewSaleOrder(saleOrderDTO, productMapper.convertAllProdClModToSupAndOrd(order));
        return "redirect:/company/" + id;
    }


}
