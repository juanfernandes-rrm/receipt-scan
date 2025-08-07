package br.ufpr.tads.receiptscan.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "PROCESSED_ITEM")
public class ProcessedItem {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "UNIT_VALUE")
    private BigDecimal unitValue;

    @Column(name = "TOTAL_VALUE")
    private BigDecimal totalValue;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RECEIPT_ID")
    private ProcessedReceipt receipt;
}
