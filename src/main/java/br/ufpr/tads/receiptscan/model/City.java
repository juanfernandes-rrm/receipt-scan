package br.ufpr.tads.receiptscan.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "CITY", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "state"})
})
public class City {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NAME", length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE", length = 50, nullable = false)
    private StateEnum state;

}
