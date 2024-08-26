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
    @ManyToMany
    @JoinTable(name = "ITEMS_RECEIPT")
    private List<Item> items;
    private Integer totalItems;
    private BigDecimal totalValue;
    private PaymentMethod paymentMethod;
    private BigDecimal valuePaid;
    private BigDecimal tax;
    @OneToOne
    private GeneralInformation generalInformation;
    private String accessKey;

}
