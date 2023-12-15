package br.ufpr.tads.repceiptscan.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Issuance {

    private LocalDateTime date;
    private String issuer;

}
