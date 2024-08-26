package br.ufpr.tads.repceiptscan.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "STORE")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 100)
    private String name;
    @Column(length = 14)
    private String CNPJ;
    @OneToOne
    private Address address;

}
