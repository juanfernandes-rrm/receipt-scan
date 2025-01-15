package br.ufpr.tads.receiptscan.dto.response;

import lombok.Data;

@Data
public class AddressDTO {
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
}
