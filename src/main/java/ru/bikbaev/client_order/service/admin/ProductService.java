package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.repository.requestRepository.ProductRequest;

import java.util.List;

@Service
public class ProductService {
    private final ProductRequest productRequest;


    public ProductService(ProductRequest productRequest) {
        this.productRequest = productRequest;
    }

    public Product findById(int id) {

        return productRequest.findById(id).orElseThrow();
    }


    public List<Product> findAll() {
        return productRequest.getAll();
    }


    public Product creatNewProduct(Product product) {
        return productRequest.creatNewProduct(product);
    }

    public void deleteProduct(int id) {
        productRequest.deleteProduct(id);
    }


    public List<Product> filterBySupplyingCompany(List<Product> products, int supplyingCompanyId) {

        return products
                .stream()
                .filter(
                        product ->
                                product
                                        .getSupplyingCompany()
                                        .getId() == supplyingCompanyId)
                .toList();
    }


    public List<Product> filterByMinBalance(List<Product> products) {

        return products
                .stream()
                .filter(
                        product ->
                                product.getStockBalance() <= product.getMinimumStockBalance())
                .toList();
    }


    public void saveAllProduct(List<Product> products) {
        productRequest.saveAll(products);
    }


}
