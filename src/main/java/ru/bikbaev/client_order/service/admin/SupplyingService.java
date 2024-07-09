package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.supplyingDTO.SupplyingCreatDTO;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.supplyingDTO.SupplyingFindDTO;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.SupplyingAndOrderProductDTO;
import ru.bikbaev.client_order.model.entity.Product;
import ru.bikbaev.client_order.model.entity.Supplying;
import ru.bikbaev.client_order.model.entity.SupplyingCompany;
import ru.bikbaev.client_order.model.entity.SupplyingProduct;
import ru.bikbaev.client_order.model.entity.compositeKey.SupplyingProductId;
import ru.bikbaev.client_order.repository.requestRepository.ProductRequest;
import ru.bikbaev.client_order.repository.requestRepository.SupplyingProductRequest;
import ru.bikbaev.client_order.repository.requestRepository.SupplyingRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupplyingService {
    private final SupplyingRequest supplyingRequest;
    private final SupplyingProductRequest supplyingProductRequest;
    private final SupplyingCompanyService supplyingCompanyService;
    private final ProductRequest productRequest;

    public SupplyingService(SupplyingRequest supplyingRequest, SupplyingProductRequest supplyingProductRequest, SupplyingCompanyService supplyingCompanyService, ProductRequest productRequest) {
        this.supplyingRequest = supplyingRequest;
        this.supplyingProductRequest = supplyingProductRequest;
        this.supplyingCompanyService = supplyingCompanyService;
        this.productRequest = productRequest;
    }


    /**
     * Создание  новой поставки
     * @param supplyingCreatDTO
     * @param supplyingProductDTOS
     * @return
     */

    public SupplyingCreatDTO creatNewSupplying(SupplyingCreatDTO supplyingCreatDTO, List<SupplyingAndOrderProductDTO> supplyingProductDTOS) {
        BigDecimal amount = BigDecimal.ZERO;

        Supplying supplying = supplyingDTOBySupplying(supplyingCreatDTO);

        supplying.setDeliveryDate(LocalDate.now());
        supplying.setDeliveryAmount(amount);

        Supplying supplyingTMP = supplyingRequest.creatNewSupplying(supplying);
        List<SupplyingProduct> products = new ArrayList<>();

        for (SupplyingAndOrderProductDTO supplyingProductDTO : supplyingProductDTOS) {

            SupplyingProduct supplyingProduct = creatSupplyingProduct(supplyingTMP, supplyingProductDTO);
            Product product = supplyingProduct.getProduct();

            amount = amount.add(product.getPurchasePrice().multiply(new BigDecimal(supplyingProductDTO.getQuantity())));

            products.add(supplyingProduct);

            int stockBalanceProduct = product.getStockBalance() + supplyingProductDTO.getQuantity();
            product.setStockBalance(stockBalanceProduct);
            productRequest.creatNewProduct(product);
        }

        supplyingTMP.setSupplyingProducts(products);
        supplyingTMP.setDeliveryAmount(amount);

        supplyingRequest.creatNewSupplying(supplyingTMP);

        supplyingCreatDTO = supplyingBySupplyingDTO(supplyingTMP);
        return supplyingCreatDTO;
    }



    public SupplyingFindDTO findById(int id) {

        Supplying supplying = supplyingRequest.findById(id).orElseThrow(()->new RuntimeException("Not found"));

        return supplyingMappedBySupplyingFindDTO(supplying);

    }


    public List<SupplyingFindDTO> findAll(){
        List<Supplying> allSupplying = supplyingRequest.getAll();

        List<SupplyingFindDTO> supplyingFindDTOS = new ArrayList<>();

        for(Supplying supplying :allSupplying){

            SupplyingFindDTO supplyingFindDTO = supplyingMappedBySupplyingFindDTO(supplying);
            supplyingFindDTOS.add(supplyingFindDTO);}

        return supplyingFindDTOS;

    }


    public void  deleteSupplying(int id){
        Supplying supplying = supplyingRequest.findById(id).orElseThrow();
        List<SupplyingProduct> supplyingProducts =  supplyingProductRequest.findBySupplyingProductId_SupplyingId(id);

        for(SupplyingProduct supplyingProduct: supplyingProducts){
            Product product = productRequest.findById(supplyingProduct.getProduct().getId()).orElseThrow();
            int quantity = product.getStockBalance() - supplyingProduct.getQuantity();
            product.setStockBalance(quantity);
            productRequest.creatNewProduct(product);

            supplyingProductRequest.deleteSupplyingProduct(supplyingProduct);
        }

        supplyingRequest.deleteSupplying(supplying);
    }



    private SupplyingFindDTO supplyingMappedBySupplyingFindDTO(Supplying supplying){
        SupplyingFindDTO supplyingFindDTO = new SupplyingFindDTO();

        supplyingFindDTO.setId(supplying.getId());
        supplyingFindDTO.setSupplyingCompanyID(supplying.getSupplyingCompany().getId());
        supplyingFindDTO.setDeliveryDate(supplying.getDeliveryDate());
        supplyingFindDTO.setDeliveryAmount(supplying.getDeliveryAmount());

        List<SupplyingProduct> supplyingProducts = findBySupplyingProductId_SupplyingId(supplying.getId());
        supplyingFindDTO.setProductDTOS(getProductDTOS(supplyingProducts));
        return supplyingFindDTO;
    }

    /**
     * метод для создания productDTOS из supplyingProduct
     * @param supplyingProducts
     * @return
     */
    private  List<SupplyingAndOrderProductDTO> getProductDTOS(List<SupplyingProduct> supplyingProducts) {
        List<SupplyingAndOrderProductDTO> supplyingProductDTOList = new ArrayList<>();

        for(SupplyingProduct supplyingProduct: supplyingProducts){
            SupplyingAndOrderProductDTO supplyingProductDTO = new SupplyingAndOrderProductDTO();

            int productId = supplyingProduct.getProduct().getId();
            int quantity = supplyingProduct.getQuantity();

            supplyingProductDTO.setProductID(productId);
            supplyingProductDTO.setQuantity(quantity);

            supplyingProductDTOList.add(supplyingProductDTO);
        }
        return supplyingProductDTOList;
    }


    /**
     * Поиск товаров по id заявки
     * @param supplyingId id Supplying
     * @return
     */
    private List<SupplyingProduct> findBySupplyingProductId_SupplyingId(int supplyingId){
        return supplyingProductRequest.findBySupplyingProductId_SupplyingId(supplyingId);
    }


    private SupplyingProduct creatSupplyingProduct(Supplying supplying, SupplyingAndOrderProductDTO supplyingProductDTO) {
        SupplyingProduct supplyingProduct = new SupplyingProduct();

        SupplyingProductId supplyingProductId = new SupplyingProductId();

        supplyingProductId.setSupplyingId(supplying.getId());
        supplyingProductId.setProductId(supplyingProductDTO.getProductID());

        supplyingProduct.setSupplyingProductId(supplyingProductId);
        supplyingProduct.setSupplying(supplying);

        Product product = productRequest.findById(supplyingProductDTO.getProductID()).orElseThrow(
                () -> new RuntimeException("Not found"));

        supplyingProduct.setProduct(product);
        supplyingProduct.setQuantity(supplyingProductDTO.getQuantity());


        return supplyingProduct;
    }


    private SupplyingCreatDTO supplyingBySupplyingDTO(Supplying supplying) {
        SupplyingCreatDTO supplyingCreatDTO = new SupplyingCreatDTO();
        supplyingCreatDTO.setId(supplying.getId());
        supplyingCreatDTO.setSupplyingCompanyID(supplying.getSupplyingCompany().getId());
        supplyingCreatDTO.setDeliveryDate(supplying.getDeliveryDate());
        supplyingCreatDTO.setDeliveryAmount(supplying.getDeliveryAmount());
        supplyingCreatDTO.setSupplyingProducts(supplying.getSupplyingProducts());
        return supplyingCreatDTO;
    }

    private Supplying supplyingDTOBySupplying(SupplyingCreatDTO supplyingCreatDTO) {
        int idSupplyingCompany = supplyingCreatDTO.getSupplyingCompanyID();

        SupplyingCompany supplyingCompany = supplyingCompanyService.findById(idSupplyingCompany);
        System.out.println(supplyingCompany);
        Supplying supplying = new Supplying();
        supplying.setId(supplyingCreatDTO.getId());
        supplying.setSupplyingCompany(supplyingCompany);
        supplying.setDeliveryDate(supplyingCreatDTO.getDeliveryDate());
        supplying.setDeliveryAmount(supplyingCreatDTO.getDeliveryAmount());
        supplying.setSupplyingProducts(supplyingCreatDTO.getSupplyingProducts());
        return supplying;
    }


}
