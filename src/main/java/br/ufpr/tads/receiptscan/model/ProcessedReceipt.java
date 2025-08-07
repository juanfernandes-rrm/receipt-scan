package br.ufpr.tads.receiptscan.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "PROCESSED_RECEIPT")
public class ProcessedReceipt {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "SCANNED_AT")
    private LocalDateTime scannedAt;

    @Column(name = "STORE_CNPJ")
    private String storeCnpj;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "STORE_ADDRESS")
    private String storeAddress;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProcessedItem> items;

    @Column(name = "TOTAL_ITEMS")
    private Integer totalItems;

    @Column(name = "TOTAL_VALUE")
    private BigDecimal totalValue;

    @Column(name = "DISCOUNT")
    private BigDecimal discount;

    @Column(name = "VALUE_TO_PAY")
    private BigDecimal valueToPay;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_METHOD")
    private PaymentMethod paymentMethod;

    @Column(name = "VALUE_PAID")
    private BigDecimal valuePaid;

    @Column(name = "TAX")
    private BigDecimal tax;

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "SERIES")
    private String series;

    @Column(name = "ISSUER")
    private String issuer;

    @Column(name = "ISSUANCE_DATE")
    private LocalDateTime issuanceDate;

    @Column(name = "AUTHORIZATION_CODE")
    private String authorizationCode;

    @Column(name = "AUTHORIZATION_DATE")
    private LocalDateTime authorizationDate;

    @Column(name = "ACCESS_KEY")
    private String accessKey;

    @Column(name = "CONSUMER_CPF")
    private String consumerCpf;

    @Column(name = "CONSUMER_NAME")
    private String consumerName;

}
