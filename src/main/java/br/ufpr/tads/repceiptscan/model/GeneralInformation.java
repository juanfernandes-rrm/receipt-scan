package br.ufpr.tads.repceiptscan.model;

import lombok.Data;

@Data
public class GeneralInformation {

    private String number;
    private String series;
    private Issuance issuance;
    private AuthorizationProtocol authorizationProtocol;

}
