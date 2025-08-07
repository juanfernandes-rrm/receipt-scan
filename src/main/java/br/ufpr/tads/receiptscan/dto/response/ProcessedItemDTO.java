package br.ufpr.tads.receiptscan.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProcessedItemDTO {
    private UUID id;
    private String productCode;
    private String productDescription;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal unitValue;
    private BigDecimal totalValue;
}
