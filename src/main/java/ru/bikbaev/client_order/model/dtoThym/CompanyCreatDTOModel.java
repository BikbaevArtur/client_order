package ru.bikbaev.client_order.model.dtoThym;


import lombok.Data;

@Data
public class CompanyCreatDTOModel {
    private int id;

    private String nameCompany;


    private String inn;


    private String paymentAccountNumbers;


    private String bikBank;
}
