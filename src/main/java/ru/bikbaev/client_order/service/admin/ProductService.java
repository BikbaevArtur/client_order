package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
import ru.bikbaev.client_order.mapped.ProductMapper;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.ProductDTO;
import ru.bikbaev.client_order.model.entity.Category;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.model.entity.SupplyingCompany;
import ru.bikbaev.client_order.repository.requestRepository.ProductRequest;

import java.util.List;

@Service
public class ProductService {
    private final ProductRequest productRequest;

    private final SupplyingCompanyService supplyingCompanyService;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    public ProductService(ProductRequest productRequest, SupplyingCompanyService supplyingCompanyService, CategoryService categoryService, ProductMapper productMapper) {
        this.productRequest = productRequest;
        this.supplyingCompanyService = supplyingCompanyService;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    public ProductDTO findById(int id) {

        Product product = productRequest.findById(id).orElseThrow();

        return    productMapper.productMappedByProductDTO(product);
    }


    public List<ProductDTO> findAll() {
        return productRequest.getAll().stream().map(productMapper::productMappedByProductDTO).toList();
    }


    public ProductDTO creatNewProduct(ProductDTO productDTO) {
        Product product = productMapper.productDTOMappedByProduct(productDTO);
        product =  productRequest.creatNewProduct(product);
        return productMapper.productMappedByProductDTO(product);
    }

    public void deleteProduct(int id) {

        Product product = productMapper.productDTOMappedByProduct(findById(id));
        productRequest.deleteProduct(product);
    }


    public List<Product> filterBySupplyingCompany(List<Product> products,int supplyingCompanyId){

        return  products
                .stream()
                .filter(
                        product ->
                                product
                                        .getSupplyingCompany()
                                        .getId() == supplyingCompanyId)
                .toList();
    }


    public List<Product> filterByMinBalance(List<Product> productDTOS){

        return productDTOS
                .stream()
                .filter(
                        product ->
                                product.getStockBalance()<= product.getMinimumStockBalance())
                .toList();
    }

    public List<Product> findAllOriginProduct(){
        return productRequest.getAll();
    }






    private Product productDTOMappedByProduct(ProductDTO productDTO) {

        SupplyingCompany supplyingCompany = supplyingCompanyService.findById(productDTO.getSupplyingCompanyId());
        Category category = categoryService.findById(productDTO.getCategoryId());


        Product product = new Product();
        product.setId(productDTO.getId());
        product.setNameProduct(productDTO.getNameProduct());
        product.setStockBalance(productDTO.getStockBalance());
        product.setMinimumStockBalance(productDTO.getMinimumStockBalance());
        product.setRetailSalePrice(productDTO.getRetailSalePrice());
        product.setInternetSalePrice(productDTO.getInternetSalePrice());
        product.setWholesaleSalePrice(productDTO.getWholesaleSalePrice());
        product.setPurchasePrice(productDTO.getPurchasePrice());
        product.setSupplyingCompany(supplyingCompany);
        product.setCategory(category);
        return product;
    }


}
