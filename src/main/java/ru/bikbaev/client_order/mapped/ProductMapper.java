package ru.bikbaev.client_order.mapped;

import org.springframework.stereotype.Component;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.ProductDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.SupplyingAndOrderProductDTO;
import ru.bikbaev.client_order.model.dtoThym.ProductClientDTOModel;
import ru.bikbaev.client_order.model.entity.Category;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.model.entity.SupplyingCompany;
import ru.bikbaev.client_order.repository.requestRepository.ProductRequest;
import ru.bikbaev.client_order.service.admin.CategoryService;
import ru.bikbaev.client_order.service.admin.SupplyingCompanyService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProductMapper {
    private final CategoryService categoryService;
    private final ProductRequest productRequest;
    private final SupplyingCompanyService supplyingCompanyService;

    public ProductMapper(CategoryService categoryService,  ProductRequest productRequest, SupplyingCompanyService supplyingCompanyService) {
        this.categoryService = categoryService;
        this.productRequest = productRequest;


        this.supplyingCompanyService = supplyingCompanyService;
    }


    public ProductClientDTOModel convertProductDTOToProdClDToMod(ProductDTO productDTO) {
        return new ProductClientDTOModel(productDTO.getId(),
                productDTO.getNameProduct(),
                productDTO.getStockBalance(),
                productDTO.getRetailSalePrice(),
                productDTO.getInternetSalePrice(),
                productDTO.getWholesaleSalePrice(),
                categoryService.findById(productDTO.getCategoryId()), 0
        );
    }

    public List<ProductClientDTOModel> convertAllProductsDTOToProdClDToMod(List<ProductDTO> productDTOS) {
        return productDTOS.stream().map(this::convertProductDTOToProdClDToMod).toList();
    }


    public SupplyingAndOrderProductDTO convertProdClModToSupAndOrd(ProductClientDTOModel productClientDTOModel) {
        SupplyingAndOrderProductDTO orderProductDTO = new SupplyingAndOrderProductDTO();
        orderProductDTO.setProductID(productClientDTOModel.getId());
        orderProductDTO.setQuantity(productClientDTOModel.getQuantity());
        return orderProductDTO;
    }

    public List<SupplyingAndOrderProductDTO> convertAllProdClModToSupAndOrd(List<ProductClientDTOModel> productClientDTOModelList) {
        return productClientDTOModelList.stream().map(this::convertProdClModToSupAndOrd).toList();
    }


    public ProductDTO productMappedByProductDTO(Product product) {
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

    public List<ProductDTO> allProductMappedByProductDTO(List<Product> products) {
        return products.stream().map(this::productMappedByProductDTO).toList();
    }


    public Product productDTOMappedByProduct(ProductDTO productDTO) {

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

    public List<Product> allProductDTOMappedByProduct(List<ProductDTO> productDTOS) {
        return productDTOS.stream().map(this::productDTOMappedByProduct).toList();
    }


    public SupplyingAndOrderProductDTO mappedMapToSupOrdProdDTO(Integer productId, String quantitySTR, String purchaseSTR) {
        int quantity = 0;
        BigDecimal purchase = BigDecimal.ZERO;

        if (quantitySTR != null && !quantitySTR.isEmpty()) {
            quantity = Integer.parseInt(quantitySTR);
        }

        if (purchaseSTR != null && !purchaseSTR.isEmpty()) {
            purchase = BigDecimal.valueOf(Integer.parseInt(purchaseSTR));
        }


        SupplyingAndOrderProductDTO orderProduct = new SupplyingAndOrderProductDTO();
        orderProduct.setProductID(productId);
        orderProduct.setQuantity(quantity);
        orderProduct.setPrice(purchase);
        return orderProduct;
    }


    public List<Product> mapperByUpdateProduct(Map<String, String> updateQuantity) {

        List<Product> productDTOList = new ArrayList<>();
        for (Map.Entry<String, String> entry : updateQuantity.entrySet()) {

            Product product = productRequest.findById(Integer.parseInt(entry.getKey())).orElseThrow(() -> new RuntimeException("product not found"));


            int quantity = Integer.parseInt(entry.getValue());


            product.setStockBalance(quantity);

            productDTOList.add(product);
        }
        return productDTOList;
    }


}
