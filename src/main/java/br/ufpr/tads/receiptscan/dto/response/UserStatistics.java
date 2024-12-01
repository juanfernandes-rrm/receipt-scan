package br.ufpr.tads.receiptscan.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
public class UserStatistics {

    private UUID userId;
    private Long totalReceipts;
    private Long totalProducts;

    public UserStatistics(UUID userId, Long totalReceipts, Long totalProducts) {
        this.userId = userId;
        this.totalReceipts = totalReceipts;
        this.totalProducts = totalProducts;
    }
}
