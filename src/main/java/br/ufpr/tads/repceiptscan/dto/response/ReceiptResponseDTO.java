package br.ufpr.tads.repceiptscan.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ReceiptResponseDTO {
    private StoreDTO store;
    private List<ItemDTO> items;
    private Integer totalItems;
    private BigDecimal totalValue;
    private String paymentMethod;
    private BigDecimal valuePaid;
    private BigDecimal tax;
    private GeneralInformationDTO generalInformation;
    private String accessKey;
}