package br.ufpr.tads.receiptscan.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
}
