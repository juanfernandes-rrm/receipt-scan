package br.ufpr.tads.receiptscan.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class StoreDTO {
    private UUID id;
    private String name;
    private String CNPJ;
    private AddressDTO address;
}
