package ru.bikbaev.client_order.controller.api.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.mapped.ProductMapper;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.ProductDTO;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.service.admin.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable int id) {
        Product product = productService.findById(id);
        ProductDTO productDTO = productMapper.productMappedByProductDTO(product);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<Product> products = productService.findAll();
        List<ProductDTO> productDTOS = productMapper.allProductMappedByProductDTO(products);
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> creatNewProduct(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.productDTOMappedByProduct(productDTO);
        product = productService.creatNewProduct(product);
        productDTO = productMapper.productMappedByProductDTO(product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
