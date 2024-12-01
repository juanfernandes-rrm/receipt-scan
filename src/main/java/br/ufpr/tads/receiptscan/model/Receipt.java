package br.ufpr.tads.receiptscan.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "RECEIPT")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "SCANNED_BY")
    private UUID scannedBy;

    @Column(name = "SCANNED_AT")
    private LocalDateTime scannedAt;

    @ManyToOne
    private Store store;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemDetails> itemDetails;

    private Integer totalItems;

    private BigDecimal totalValue;

    private BigDecimal discount;

    private BigDecimal valueToPay;

    private PaymentMethod paymentMethod;

    private BigDecimal valuePaid;

    private BigDecimal tax;

    @OneToOne
    private GeneralInformation generalInformation;

    private String accessKey;

}
