package br.ufpr.tads.receiptscan.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptRequestDTO {

    @NotBlank(message = "NFE Url is mandatory")
    private String url;

}
