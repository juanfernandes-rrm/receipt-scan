package br.ufpr.tads.repceiptscan.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class ReceiptResponseDTO {
    private StoreDTO store;
    private List<ItemDTO> items;
    private Integer totalItems;
    private BigDecimal totalValue;
    private BigDecimal discount;
    private BigDecimal valueToPay;
    private String paymentMethod;
    private BigDecimal valuePaid;
    private BigDecimal tax;
    private GeneralInformationDTO generalInformation;
    private String accessKey;
}