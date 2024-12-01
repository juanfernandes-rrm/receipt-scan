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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RECEIPT_ID", nullable = false)
    private Receipt receipt;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private BigDecimal unitValue;

    @Column(nullable = false)
    private BigDecimal totalValue;

}
