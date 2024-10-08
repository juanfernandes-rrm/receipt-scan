package br.ufpr.tads.repceiptscan.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "RECEIPT")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
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
