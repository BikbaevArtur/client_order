package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
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

    public ProductService(ProductRequest productRequest, SupplyingCompanyService supplyingCompanyService, CategoryService categoryService) {
        this.productRequest = productRequest;
        this.supplyingCompanyService = supplyingCompanyService;
        this.categoryService = categoryService;
    }

    public ProductDTO findById(int id) {

        Product product = productRequest.findById(id).orElseThrow();
        return productMappedByProductDTO(product);
    }


    public List<ProductDTO> findAll() {
        return productRequest.getAll().stream().map(this::productMappedByProductDTO).toList();
    }


    public ProductDTO creatNewProduct(ProductDTO productDTO) {
        Product product = productDTOMappedByProduct(productDTO);
        Product temp =  productRequest.creatNewProduct(product);
        product.setId(temp.getId());
        return productMappedByProductDTO(temp);
    }

    public void deleteProduct(int id) {

        Product product = productDTOMappedByProduct(findById(id));
        productRequest.deleteProduct(product);
    }


    private ProductDTO productMappedByProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setNameProduct(product.getNameProduct());
        productDTO.setStockBalance(product.getStockBalance());
        productDTO.setMinimumStockBalance(product.getMinimumStockBalance());
        productDTO.setRetailSalePrice(product.getRetailSalePrice());
        productDTO.setInternetSalePrice(product.getInternetSalePrice());
        productDTO.setWholesaleSalePrice(product.getWholesaleSalePrice());
        productDTO.setPurchasePrice(product.getPurchasePrice());
        productDTO.setSupplyingCompanyId(product.getSupplyingCompany().getId());
        productDTO.setCategoryId(product.getCategory().getId());
        return productDTO;
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
