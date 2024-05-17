package br.ufpr.tads.repceiptscan.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "GENERAL_INFORMATION")
public class GeneralInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String number;
    private String series;
    @OneToOne
    private Issuance issuance;
    @OneToOne
    private AuthorizationProtocol authorizationProtocol;

}
