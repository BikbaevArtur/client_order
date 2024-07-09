package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.SaleOrderDTO;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.SupplyingAndOrderProductDTO;
import ru.bikbaev.client_order.model.entity.*;
import ru.bikbaev.client_order.model.entity.compositeKey.OrderProductId;
import ru.bikbaev.client_order.repository.requestRepository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleOrderService {

    private final SaleOrderRequest saleOrderRequest;
    private final ProductRequest productRequest;
    private final StatusOrderRequest statusOrderRequest;
    private final OrderProductRequest orderProductRequest;
    private final CompanyRequest companyRequest;

    public SaleOrderService(SaleOrderRequest saleOrderRequest, ProductRequest productRequest, StatusOrderRequest statusOrderRequest, OrderProductRequest orderProductRequest, CompanyRequest companyRequest) {
        this.saleOrderRequest = saleOrderRequest;
        this.productRequest = productRequest;
        this.statusOrderRequest = statusOrderRequest;
        this.orderProductRequest = orderProductRequest;
        this.companyRequest = companyRequest;
    }

    /**
     * Creates a new sale order and saves it in the database.
     *
     * @param saleOrderDTO                    the DTO containing sale order details
     * @param supplyingAndOrderProductDTOList the list of products associated with the sale order
     * @return the DTO of the created sale order
     */

    @Transactional
    public SaleOrderDTO creatNewSaleOrder(SaleOrderDTO saleOrderDTO, List<SupplyingAndOrderProductDTO> supplyingAndOrderProductDTOList) {

        SaleOrder saleOrder = creatSaleOrderFromDTO(saleOrderDTO);
        saleOrder = saleOrderRequest.creatNewSaleOrder(saleOrder);

        List<OrderProduct> orderProducts = getOrderProduct(supplyingAndOrderProductDTOList, saleOrder.getId());

        BigDecimal amount = calculateTotalAmount(orderProducts);

        saleOrder.setOrderProducts(orderProducts);
        saleOrder.setAmountOrder(amount);

        saleOrderRequest.creatNewSaleOrder(saleOrder);


        return saleOrderConvertToDTO(saleOrder, supplyingAndOrderProductDTOList);


    }


    public SaleOrderDTO findOrderById(int id) {

        SaleOrder saleOrder = saleOrderRequest.findById(id).orElseThrow(
                () -> new RuntimeException("order not found"));

        List<SupplyingAndOrderProductDTO> orderProductDTOS = getOrderProductDTOS(saleOrder.getOrderProducts());

        return saleOrderConvertToDTO(saleOrder, orderProductDTOS);
    }


    /**
     * TODO после заполнение статусов заявок , изменить логику изменения кол - во продукта
     * @param id
     */

    public void deleteSaleOrder(int id) {
        SaleOrder saleOrder = saleOrderRequest.findById(id).orElseThrow(
                () -> new RuntimeException("order not found"));

        List<OrderProduct> orderProducts = orderProductRequest.findByOrderProductId_SaleOrderId(id);

        for (OrderProduct orderProduct: orderProducts){

            if(saleOrder.getStatusOrder().getId() == 4){
                Product product = orderProduct.getProduct();

                int quantity = product.getStockBalance() + orderProduct.getQuantity();
                product.setStockBalance(quantity);
                productRequest.creatNewProduct(product);
            }


            orderProductRequest.deleteOrderProduct(orderProduct);
        }

        saleOrderRequest.deleteSaleOrder(saleOrder);

    }


    /**
     * Creates a SaleOrder entity from the provided DTO.
     *
     * @param saleOrderDTO the DTO containing sale order details
     * @return the SaleOrder entity
     */
    private SaleOrder creatSaleOrderFromDTO(SaleOrderDTO saleOrderDTO) {

        Company company = companyRequest.findById(saleOrderDTO.getCompanyId()).orElseThrow(
                () -> new RuntimeException("company not found"));

        StatusOrder statusOrder = statusOrderRequest.findById(1).orElseThrow(
                () -> new RuntimeException("status not found"));

        LocalDate localDate = LocalDate.now();

        SaleOrder saleOrder = new SaleOrder();

        saleOrder.setId(saleOrder.getId());
        saleOrder.setCompany(company);
        saleOrder.setDataOrder(localDate);
        saleOrder.setAmountOrder(BigDecimal.ZERO);
        saleOrder.setStatusOrder(statusOrder);
        saleOrder.setCheckLink(saleOrderDTO.getCheckLink());

        return saleOrder;
    }


    /**
     * Creates a list of OrderProduct entities from the provided DTO list and sale order ID.
     *
     * @param orderProductDTOList the list of DTOs containing product details
     * @param saleOrderId         the ID of the sale order
     * @return the list of OrderProduct entities
     */
    private List<OrderProduct> getOrderProduct(List<SupplyingAndOrderProductDTO> orderProductDTOList, int saleOrderId) {

        List<OrderProduct> orderProducts = new ArrayList<>();

        SaleOrder saleOrder = saleOrderRequest.findById(saleOrderId).orElseThrow(
                () -> new RuntimeException(" saleOrder not found"));

        for (SupplyingAndOrderProductDTO orderProductDTO : orderProductDTOList) {

            Product product = productRequest.findById(orderProductDTO.getProductID()).orElseThrow(
                    () -> new RuntimeException("product not found"));

            OrderProductId orderProductId = new OrderProductId(saleOrderId, orderProductDTO.getProductID());

            OrderProduct orderProduct = new OrderProduct();

            orderProduct.setOrderProductId(orderProductId);
            orderProduct.setSaleOrder(saleOrder);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(orderProductDTO.getQuantity());

            orderProducts.add(orderProduct);

        }

        return orderProducts;

    }

    /**
     * Calculates the total amount of the sale order.
     *
     * @param orderProducts the list of OrderProduct entities
     * @return the total amount of the sale order
     */
    private BigDecimal calculateTotalAmount(List<OrderProduct> orderProducts) {
        BigDecimal amount = BigDecimal.ZERO;

        for (OrderProduct orderProduct : orderProducts) {
            BigDecimal price = orderProduct.getProduct().getPurchasePrice();
            BigDecimal quantity = BigDecimal.valueOf(orderProduct.getQuantity());
            amount = amount.add(price.multiply(quantity));
        }

        return amount;

    }


    /**
     * Converts a SaleOrder entity to a SaleOrderDTO.
     *
     * @param saleOrder      the SaleOrder entity
     * @param productDTOList the list of product DTOs
     * @return the SaleOrderDTO
     */
    private SaleOrderDTO saleOrderConvertToDTO(SaleOrder saleOrder, List<SupplyingAndOrderProductDTO> productDTOList) {

        SaleOrderDTO saleOrderDTO = new SaleOrderDTO();

        saleOrderDTO.setId(saleOrder.getId());
        saleOrderDTO.setCompanyId(saleOrder.getCompany().getId());
        saleOrderDTO.setDataOrder(saleOrder.getDataOrder());
        saleOrderDTO.setAmountOrder(saleOrder.getAmountOrder());
        saleOrderDTO.setStatusOrderID(saleOrder.getStatusOrder().getId());
        saleOrderDTO.setCheckLink(saleOrder.getCheckLink());
        saleOrderDTO.setProductDTOS(productDTOList);
        return saleOrderDTO;

    }


    private List<SupplyingAndOrderProductDTO> getOrderProductDTOS(List<OrderProduct> orderProducts) {

        List<SupplyingAndOrderProductDTO> orderProductDTOS = new ArrayList<>();

        for (OrderProduct orderProduct : orderProducts) {
            SupplyingAndOrderProductDTO orderProductDTO = new SupplyingAndOrderProductDTO();

            orderProductDTO.setProductID(orderProduct.getProduct().getId());
            orderProductDTO.setQuantity(orderProduct.getQuantity());

            orderProductDTOS.add(orderProductDTO);
        }

        return orderProductDTOS;
    }


}
