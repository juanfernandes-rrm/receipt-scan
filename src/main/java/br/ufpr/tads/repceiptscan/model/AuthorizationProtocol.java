package br.ufpr.tads.repceiptscan.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthorizationProtocol {

    private String code;
    private LocalDateTime date;
}
