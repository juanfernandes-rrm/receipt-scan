package br.ufpr.tads.repceiptscan.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {

    private String name;
    private String code;
    private BigDecimal amount;
    private String unit;
    private BigDecimal unitValue;
    private BigDecimal totalValue;

}
