package br.ufpr.tads.repceiptscan.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralInformationDTO {
    private String number;
    private String series;
    private IssuanceDTO issuance;
    private AuthorizationProtocolDTO authorizationProtocol;
}
