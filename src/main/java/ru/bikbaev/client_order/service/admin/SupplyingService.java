package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.SupplyingAndOrderProductDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.supplyingDTO.SupplyingDTO;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.model.entity.Supplying;
import ru.bikbaev.client_order.model.entity.SupplyingCompany;
import ru.bikbaev.client_order.model.entity.SupplyingProduct;
import ru.bikbaev.client_order.model.entity.compositeKey.SupplyingProductId;
import ru.bikbaev.client_order.repository.requestRepository.ProductRequest;
import ru.bikbaev.client_order.repository.requestRepository.SupplyingCompanyRequest;
import ru.bikbaev.client_order.repository.requestRepository.SupplyingProductRequest;
import ru.bikbaev.client_order.repository.requestRepository.SupplyingRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * TODO отредактировать под формат OrderProduct
 */
@Service
public class SupplyingService {
    private final SupplyingRequest supplyingRequest;
    private final SupplyingProductRequest supplyingProductRequest;
    private final SupplyingCompanyRequest supplyingCompanyRequest;
    private final ProductRequest productRequest;

    public SupplyingService(SupplyingRequest supplyingRequest, SupplyingProductRequest supplyingProductRequest,  SupplyingCompanyRequest supplyingCompanyRequest, ProductRequest productRequest) {
        this.supplyingRequest = supplyingRequest;
        this.supplyingProductRequest = supplyingProductRequest;
        this.supplyingCompanyRequest = supplyingCompanyRequest;
        this.productRequest = productRequest;
    }


    /**
     * Creates a new supplying record along with its associated products.
     * The total delivery amount is calculated based on the products' purchase prices and quantities.
     *
     * @param supplyingCreatDTO    the supplying DTO containing supplying details
     * @param supplyingProductDTOS the list of supplying and order product DTOs
     * @return the created supplying DTO with updated information
     */

    @Transactional
    public SupplyingDTO creatNewSupplying(SupplyingDTO supplyingCreatDTO, List<SupplyingAndOrderProductDTO> supplyingProductDTOS) {
        Supplying supplying = creatSupplyingFromDTO(supplyingCreatDTO);
        supplying = supplyingRequest.creatNewSupplying(supplying);

        List<SupplyingProduct> supplyingProducts = getSupplyingProducts(supplyingProductDTOS, supplying.getId());

        BigDecimal amount = calculateTotalAmount(supplyingProducts);

        supplying.setSupplyingProducts(supplyingProducts);
        supplying.setDeliveryAmount(amount);

        supplyingRequest.creatNewSupplying(supplying);

        return supplyingConvertToDTO(supplying, supplyingProductDTOS);
    }


    /**
     * Finds a supplying record by its ID and converts it to a SupplyingDTO.
     *
     * @param id the ID of the supplying record
     * @return the found supplying DTO
     */
    public SupplyingDTO findById(int id) {

        Supplying supplying = supplyingRequest.findById(id).orElseThrow(
                () -> new RuntimeException("supplying not found"));

        List<SupplyingAndOrderProductDTO> productDTOS = getProductDTOS(supplying.getSupplyingProducts());

        return supplyingConvertToDTO(supplying, productDTOS);

    }


    /**
     * Deletes a supplying record by its ID.
     * Updates the stock balance of each product associated with the supplying record.
     *
     * @param id the ID of the supplying record
     */
    @Transactional
    public void deleteSupplying(int id) {
        Supplying supplying = supplyingRequest.findById(id).orElseThrow(
                () -> new RuntimeException(" supplying not found"));

        List<SupplyingProduct> supplyingProducts = supplyingProductRequest.findBySupplyingProductId_SupplyingId(id);

        for (SupplyingProduct supplyingProduct : supplyingProducts) {

            Product product = productRequest.findById(supplyingProduct.getProduct().getId()).orElseThrow();

            int quantity = product.getStockBalance() - supplyingProduct.getQuantity();
            product.setStockBalance(quantity);
            productRequest.creatNewProduct(product);

            supplyingProductRequest.deleteSupplyingProduct(supplyingProduct);
        }

        supplyingRequest.deleteSupplying(supplying);
    }

    /**
     * Finds all supplying records and converts them to a list of SupplyingDTOs.
     *
     * @return the list of all supplying DTOs
     */
    public List<SupplyingDTO> findAllSupplying() {
        List<Supplying> allSupplying = supplyingRequest.getAll();

        List<SupplyingDTO> supplyingDTOS = new ArrayList<>();

        for (Supplying supplying : allSupplying) {
            List<SupplyingAndOrderProductDTO> productDTOS = getProductDTOS(supplying.getSupplyingProducts());
            SupplyingDTO supplyingDTO = supplyingConvertToDTO(supplying, productDTOS);

            supplyingDTOS.add(supplyingDTO);

        }

        return supplyingDTOS;

    }


    public List<Supplying> findAllOriginSupplying(){
        return supplyingRequest.getAll();
    }



    /**
     * Creates a Supplying entity from a SupplyingDTO.
     *
     * @param supplyingDTO the supplying DTO containing supplying details
     * @return the created supplying entity
     */
    private Supplying creatSupplyingFromDTO(SupplyingDTO supplyingDTO) {


        SupplyingCompany supplyingCompany = supplyingCompanyRequest.findById(supplyingDTO.getSupplyingCompanyID()).orElseThrow(
                () -> new RuntimeException("supplying company not found"));

        LocalDate localDate = LocalDate.now();

        Supplying supplying = new Supplying();

        supplying.setId(supplyingDTO.getId());
        supplying.setSupplyingCompany(supplyingCompany);
        supplying.setDeliveryDate(localDate);
        supplying.setDeliveryAmount(BigDecimal.ZERO);

        return supplying;
    }

    /**
     * Converts a list of SupplyingAndOrderProductDTO to SupplyingProduct entities.
     *
     * @param productDTOS the list of supplying and order product DTOs
     * @param supplyingID the ID of the supplying record
     * @return the list of supplying products
     */
    List<SupplyingProduct> getSupplyingProducts(List<SupplyingAndOrderProductDTO> productDTOS, int supplyingID) {

        List<SupplyingProduct> supplyingProducts = new ArrayList<>();

        Supplying supplying = supplyingRequest.findById(supplyingID).orElseThrow(
                () -> new RuntimeException("supplying not found"));

        for (SupplyingAndOrderProductDTO supplyingProductDTO : productDTOS) {

            Product product = productRequest.findById(supplyingProductDTO.getProductID()).orElseThrow(
                    () -> new RuntimeException("product not found"));

            int stockBalance = product.getStockBalance() + supplyingProductDTO.getQuantity();

            BigDecimal purchasePrice = product.getPurchasePrice();
            if(!supplyingProductDTO.getPrice().equals(BigDecimal.ZERO)){
                purchasePrice = supplyingProductDTO.getPrice();
            }

            product.setStockBalance(stockBalance);
            product.setPurchasePrice(purchasePrice);

            product = productRequest.creatNewProduct(product);

            SupplyingProductId supplyingProductId = new SupplyingProductId(supplyingID, supplyingProductDTO.getProductID());

            SupplyingProduct supplyingProduct = new SupplyingProduct();

            supplyingProduct.setSupplyingProductId(supplyingProductId);
            supplyingProduct.setSupplying(supplying);
            supplyingProduct.setProduct(product);
            supplyingProduct.setQuantity(supplyingProductDTO.getQuantity());

            supplyingProducts.add(supplyingProduct);
        }
        return supplyingProducts;
    }


    private BigDecimal calculateTotalAmount(List<SupplyingProduct> supplyingProducts) {
        BigDecimal amount = BigDecimal.ZERO;

        for (SupplyingProduct supplyingProduct : supplyingProducts) {
            BigDecimal price = supplyingProduct.getProduct().getPurchasePrice();
            BigDecimal quantity = BigDecimal.valueOf(supplyingProduct.getQuantity());
            amount = amount.add(price.multiply(quantity));
        }

        return amount;

    }


    private SupplyingDTO supplyingConvertToDTO(Supplying supplying, List<SupplyingAndOrderProductDTO> productDTOS) {
        SupplyingDTO supplyingCreatDTO = new SupplyingDTO();
        supplyingCreatDTO.setId(supplying.getId());
        supplyingCreatDTO.setSupplyingCompanyID(supplying.getSupplyingCompany().getId());
        supplyingCreatDTO.setDeliveryDate(supplying.getDeliveryDate());
        supplyingCreatDTO.setDeliveryAmount(supplying.getDeliveryAmount());
        supplyingCreatDTO.setProductDTOS(productDTOS);
        return supplyingCreatDTO;
    }



    private List<SupplyingAndOrderProductDTO> getProductDTOS(List<SupplyingProduct> supplyingProducts) {
        List<SupplyingAndOrderProductDTO> supplyingProductDTOList = new ArrayList<>();

        for (SupplyingProduct supplyingProduct : supplyingProducts) {
            SupplyingAndOrderProductDTO supplyingProductDTO = new SupplyingAndOrderProductDTO();

            supplyingProductDTO.setProductID(supplyingProduct.getProduct().getId());
            supplyingProductDTO.setQuantity(supplyingProduct.getQuantity());

            supplyingProductDTOList.add(supplyingProductDTO);
        }
        return supplyingProductDTOList;
    }
}
