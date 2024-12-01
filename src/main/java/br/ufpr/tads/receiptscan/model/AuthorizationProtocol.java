package br.ufpr.tads.receiptscan.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "AUTHORIZATION_PROTOCOL")
public class AuthorizationProtocol {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String code;
    private LocalDateTime date;
}
