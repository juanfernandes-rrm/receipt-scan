package br.ufpr.tads.repceiptscan.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDTO {
    private String name;
    private String CNPJ;
    private AddressDTO address;
}
