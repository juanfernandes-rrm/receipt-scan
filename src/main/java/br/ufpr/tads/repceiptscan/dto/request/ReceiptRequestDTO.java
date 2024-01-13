package br.ufpr.tads.repceiptscan.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReceiptRequestDTO {

    @NotBlank(message = "NFE Url is mandatory")
    private String url;

}
