package br.ufpr.tads.receiptscan.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

    private String type;
    private String title;
    private int status;
    private String detail;

}

