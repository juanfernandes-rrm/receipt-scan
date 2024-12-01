package br.ufpr.tads.receiptscan.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReceiptSummaryResponseDTO {

    private LocalDateTime scannedAt;
    private List<ItemDTO> items;
    private BigDecimal totalValue;

}
