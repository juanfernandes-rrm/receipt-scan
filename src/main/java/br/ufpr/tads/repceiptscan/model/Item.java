package br.ufpr.tads.repceiptscan.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "ITEM")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 150)
    private String name;
    private String code;

}
