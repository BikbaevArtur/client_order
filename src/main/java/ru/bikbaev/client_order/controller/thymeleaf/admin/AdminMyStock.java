package ru.bikbaev.client_order.controller.thymeleaf.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.mapped.ProductMapper;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.ProductDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.SupplyingAndOrderProductDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.supplyingDTO.SupplyingDTO;
import ru.bikbaev.client_order.model.entity.Category;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.model.entity.Supplying;
import ru.bikbaev.client_order.model.entity.SupplyingCompany;
import ru.bikbaev.client_order.service.admin.CategoryService;
import ru.bikbaev.client_order.service.admin.ProductService;
import ru.bikbaev.client_order.service.admin.SupplyingCompanyService;
import ru.bikbaev.client_order.service.admin.SupplyingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminMyStock {
    private final ProductService productService;
    private final SupplyingCompanyService supplyingCompanyService;
    private final CategoryService categoryService;
    private final SupplyingService supplyingService;
    private final ProductMapper productMapper;


    public AdminMyStock(ProductService productService, SupplyingCompanyService supplyingCompanyService, CategoryService categoryService, SupplyingService service, SupplyingService supplyingService, ProductMapper productMapper, ProductMapper productMapper1) {
        this.productService = productService;
        this.supplyingCompanyService = supplyingCompanyService;
        this.categoryService = categoryService;
        this.supplyingService = supplyingService;
        this.productMapper = productMapper1;
    }


    @GetMapping("/stock")
    public String getStock(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin_page/admin-stock";
    }


    @GetMapping("/creat-product")
    public String formCreatNewProduct(Model model, ProductDTO product) {
        List<SupplyingCompany> supplyingCompanies = supplyingCompanyService.findAll();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("supplyingCompany", supplyingCompanies);
        model.addAttribute("product", product);
        return "admin_page/admin-creat-product";
    }

    @PostMapping("/creat-product")
    public String creatNewProduct(ProductDTO productDTO) {
        Product product = productMapper.productDTOMappedByProduct(productDTO);
        productService.creatNewProduct(product);
        return "redirect:/admin/stock";
    }


    @GetMapping("/supplying-orders")
    public String supplyingOrder(Model model) {
        List<Supplying> supplying = supplyingService.findAllOriginSupplying();
        model.addAttribute("supplyingOrders", supplying);
        return "/admin_page/admin-supplying-orders";
    }


    @GetMapping("/supplying-orders/supplying-company")
    public String supplyingCompany(Model model) {
        List<SupplyingCompany> supplyingCompanies = supplyingCompanyService.findAll();
        model.addAttribute("supplyingCompanies", supplyingCompanies);
        return "/admin_page/admin-supplying-company";
    }


    @GetMapping("/supplying-orders/supplying-company/new-supplying/{id}")
    public String creatNewSupplyingForm(Model model, @PathVariable int id) {
        List<Product> products = productService.findAll();
        List<Product> filterProduct = productService.filterBySupplyingCompany(products, id);
        model.addAttribute("products", filterProduct);
        model.addAttribute("supplyingCompanyId", id);
        return "/admin_page/admin-new-supplying";
    }


    @PostMapping("/supplying-orders/supplying-company/new-supplying/{id}")
    public String creatNewSupplying(@PathVariable int id,
                                    @RequestParam(value = "selectedProducts", required = false) List<Integer> selectedProductId,
                                    @RequestParam Map<String, String> quantitiesPurchase) {

        List<SupplyingAndOrderProductDTO> supplyingAndOrderProductDTOS = new ArrayList<>();
        if (selectedProductId != null) {
            for (Integer productId : selectedProductId) {
                String quantitySTR = quantitiesPurchase.get("quantities[" + productId + "]");
                String purchaseSTR = quantitiesPurchase.get("purchase[" + productId + "]");

                SupplyingAndOrderProductDTO orderProduct = productMapper.mappedMapToSupOrdProdDTO(productId, quantitySTR, purchaseSTR);

                supplyingAndOrderProductDTOS.add(orderProduct);
            }


        }


        SupplyingDTO supplyingDTO = new SupplyingDTO();
        supplyingDTO.setSupplyingCompanyID(id);

        supplyingService.creatNewSupplying(supplyingDTO,supplyingAndOrderProductDTOS);


        return "redirect:/admin/supplying-orders";
    }


}
