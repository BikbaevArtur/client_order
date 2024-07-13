package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.SupplyingAndOrderProductDTO;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.orderDTO.InvoiceForConfirmationOrderDTO;
import ru.bikbaev.client_order.model.dto.dtoAdminPanel.orderDTO.SaleOrderDTO;
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



    /**
     * Updates the status and check link of a sale order based on the provided invoice details.
     *
     * @param invoice The InvoiceForConfirmationOrderDTO containing the sale order ID, new status ID, and check link.
     * @return The updated SaleOrderDTO representing the sale order with the new status and check link.
     * @throws RuntimeException if the sale order or status is not found.
     */
    public SaleOrderDTO invoiceForConfirmationOrder(InvoiceForConfirmationOrderDTO invoice) {

        SaleOrder saleOrder = saleOrderRequest.findById(invoice.getSaleOrderId()).orElseThrow(
                () -> new RuntimeException("order not found"));

        System.out.println(invoice.getStatusOrderId());
        StatusOrder statusOrder = statusOrderRequest.findById(invoice.getStatusOrderId()).orElseThrow(
                () -> new RuntimeException("status not found"));


        saleOrder.setStatusOrder(statusOrder);
        saleOrder.setCheckLink(invoice.getCheckLink());

        saleOrderRequest.creatNewSaleOrder(saleOrder);

        return saleOrderConvertToDTO(saleOrder,getOrderProductDTOS(saleOrder.getOrderProducts()));

    }


    /**
     * Updates the status of a sale order and adjusts the product stock balance if necessary.
     *
     * @param saleOrderId   The ID of the sale order to be updated.
     * @param statusOrderId The ID of the new status to be set for the sale order.
     * @return The updated SaleOrderDTO representing the sale order with the new status.
     * @throws RuntimeException if the sale order or status is not found.
     */
    @Transactional
    public SaleOrderDTO updateOrderStatus(int saleOrderId, int statusOrderId) {
        SaleOrder saleOrder = saleOrderRequest.findById(saleOrderId).orElseThrow(
                () -> new RuntimeException("order not found"));
        StatusOrder statusOrder = statusOrderRequest.findById(statusOrderId).orElseThrow(
                () -> new RuntimeException("status not found"));

        if (statusOrderId == 3 || saleOrderId == 4) {
            List<OrderProduct> orderProducts = saleOrder.getOrderProducts();
            for (OrderProduct orderProduct : orderProducts) {
                Product product = orderProduct.getProduct();
                int quantityProduct = product.getStockBalance() - orderProduct.getQuantity();
                product.setStockBalance(quantityProduct);
                productRequest.creatNewProduct(product);
            }
        }

        saleOrder.setStatusOrder(statusOrder);
        saleOrderRequest.creatNewSaleOrder(saleOrder);
        return saleOrderConvertToDTO(saleOrder, getOrderProductDTOS(saleOrder.getOrderProducts()));
    }


    /**
     * Finds a sale order by its ID and converts it to a SaleOrderDTO.
     *
     * @param id The unique identifier of the sale order to be found.
     * @return A SaleOrderDTO representing the sale order, including its associated products.
     * @throws RuntimeException if the sale order is not found.
     */
    public SaleOrderDTO findOrderById(int id) {

        SaleOrder saleOrder = saleOrderRequest.findById(id).orElseThrow(
                () -> new RuntimeException("order not found"));

        List<SupplyingAndOrderProductDTO> orderProductDTOS = getOrderProductDTOS(saleOrder.getOrderProducts());

        return saleOrderConvertToDTO(saleOrder, orderProductDTOS);
    }


    /**
     * Deletes a sale order and its associated order products. If the status of the sale order
     * is either 3(заявка подтверждена) or 4(отгружено), it updates the stock balance of the related products before deleting
     * the order products and the sale order.
     *
     * @param id The unique identifier of the sale order to be deleted.
     * @throws RuntimeException if the sale order is not found.
     */

    @Transactional
    public void deleteSaleOrder(int id) {
        SaleOrder saleOrder = saleOrderRequest.findById(id).orElseThrow(
                () -> new RuntimeException("order not found"));

        List<OrderProduct> orderProducts = orderProductRequest.findByOrderProductId_SaleOrderId(id);

        for (OrderProduct orderProduct : orderProducts) {

            if (saleOrder.getStatusOrder().getId() == 3 || saleOrder.getStatusOrder().getId() == 4) {
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
     * Retrieves all sale orders and converts them to a list of SaleOrderDTOs.
     *
     * @return A list of SaleOrderDTOs representing all sale orders and their associated products.
     */

    public List<SaleOrderDTO> findAllOrder() {

        List<SaleOrder> allSaleOrders = saleOrderRequest.getAll();

        List<SaleOrderDTO> saleOrderDTOS = new ArrayList<>();

        for (SaleOrder saleOrder : allSaleOrders) {
            List<SupplyingAndOrderProductDTO> orderProductDTOS = getOrderProductDTOS(saleOrder.getOrderProducts());
            SaleOrderDTO saleOrderDTO = saleOrderConvertToDTO(saleOrder, orderProductDTOS);

            saleOrderDTOS.add(saleOrderDTO);

        }

        return saleOrderDTOS;

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


    /**
     * Converts a list of OrderProduct entities to a list of SupplyingAndOrderProductDTOs.
     * This method iterates through each OrderProduct and creates a corresponding
     * SupplyingAndOrderProductDTO with the product ID and quantity.
     *
     * @param orderProducts The list of OrderProduct entities to be converted.
     * @return A list of SupplyingAndOrderProductDTOs.
     */

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
