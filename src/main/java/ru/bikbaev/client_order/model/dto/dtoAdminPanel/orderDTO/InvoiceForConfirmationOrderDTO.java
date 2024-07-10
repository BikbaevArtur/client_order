package ru.bikbaev.client_order.model.dto.dtoAdminPanel.orderDTO;

import lombok.Getter;


@Getter
public class InvoiceForConfirmationOrderDTO {

    private final int saleOrderId ;
    private final int statusOrderId;
    private final String checkLink;

    public InvoiceForConfirmationOrderDTO(int saleOrderId,  String checkLink) {
        this.saleOrderId = saleOrderId;
        this.statusOrderId = 2;
        this.checkLink = checkLink;
    }
}
