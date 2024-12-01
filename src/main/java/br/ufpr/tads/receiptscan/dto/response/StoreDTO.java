package br.ufpr.tads.receiptscan.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StoreDTO {
    private UUID id;
    private String name;
    private String CNPJ;
    private AddressDTO address;
}
