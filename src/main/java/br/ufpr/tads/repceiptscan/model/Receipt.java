package br.ufpr.tads.repceiptscan.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Receipt {

    private Store store;
    private List<Item> items;
    private Integer totalItems;
    private BigDecimal totalValue;
    private String paymentMethod;
    private BigDecimal valuePaid;
    private BigDecimal tax;
    private GeneralInformation generalInformation;
    private String accessKey;

}
