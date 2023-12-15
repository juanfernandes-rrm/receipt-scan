package br.ufpr.tads.repceiptscan.model;

import lombok.Data;

@Data
public class Store {

    private String name;
    private String CNPJ;
    private Address address;

}
