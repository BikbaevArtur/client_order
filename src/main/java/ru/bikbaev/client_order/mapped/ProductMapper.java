package ru.bikbaev.client_order.mapped;

import org.springframework.stereotype.Component;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.ProductDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.SupplyingAndOrderProductDTO;
import ru.bikbaev.client_order.model.dtoThym.ProductClientDTOModel;
import ru.bikbaev.client_order.service.admin.CategoryService;
import ru.bikbaev.client_order.service.admin.ProductService;

import java.util.List;
@Component
public class ProductMapper {
    private final CategoryService categoryService;
    private final ProductService productService;

    public ProductMapper(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    public ProductClientDTOModel convertProductDTOToProdClDToMod(ProductDTO productDTO) {
        return new ProductClientDTOModel(productDTO.getId(),
                productDTO.getNameProduct(),
                productDTO.getStockBalance(),
                productDTO.getRetailSalePrice(),
                productDTO.getInternetSalePrice(),
                productDTO.getWholesaleSalePrice(),
                categoryService.findById(productDTO.getId()),0
        );
    }

    public List<ProductClientDTOModel> convertAllProductsDTOToProdClDToMod(List<ProductDTO> productDTOS) {
        return productDTOS.stream().map(this::convertProductDTOToProdClDToMod).toList();
    }

    public List<SupplyingAndOrderProductDTO> convertAllProdClModToSupAndOrd(List<ProductClientDTOModel> productClientDTOModelList){
        return productClientDTOModelList.stream().map(this::convertProdClModToSupAndOrd).toList();
    }

    public SupplyingAndOrderProductDTO convertProdClModToSupAndOrd(ProductClientDTOModel productClientDTOModel){
        return new SupplyingAndOrderProductDTO(productClientDTOModel.getId(),productClientDTOModel.getQuantity());
    }




    public ProductDTO convertProdClDTOModToProductDTO(ProductClientDTOModel productClientDTOModel){
        return productService.findById(productClientDTOModel.getId());
    }



}
