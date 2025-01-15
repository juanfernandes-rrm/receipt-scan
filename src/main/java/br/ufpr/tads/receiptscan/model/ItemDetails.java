package br.ufpr.tads.receiptscan.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "ITEM_DETAILS")
public class ItemDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RECEIPT_ID", nullable = false)
    private Receipt receipt;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "UNIT", nullable = false)
    private String unit;

    @Column(name = "UNIT_VALUE", nullable = false)
    private BigDecimal unitValue;

    @Column(name = "TOTAL_VALUE", nullable = false)
    private BigDecimal totalValue;

}
