package ru.bikbaev.client_order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyDTO {

    private int id;
    private String nameCompany;
    private String inn;
    private String paymentAccountNumbers;
    private String bikBank;
    private int userId;
}
