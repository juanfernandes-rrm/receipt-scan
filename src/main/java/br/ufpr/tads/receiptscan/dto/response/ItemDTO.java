package br.ufpr.tads.receiptscan.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {
    private String name;
    private String code;
    private BigDecimal amount;
    private String unit;
    private BigDecimal unitValue;
    private BigDecimal totalValue;
}
