package br.ufpr.tads.repceiptscan.controller;

import br.ufpr.tads.repceiptscan.dto.ReceiptRequestDTO;
import br.ufpr.tads.repceiptscan.service.ReceiptScanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scan")
public class ReceiptScanController {

    @Autowired
    private ReceiptScanService receiptScanService;

    @PostMapping
    public ResponseEntity<?> scan(@RequestBody @Valid ReceiptRequestDTO receiptRequestDTO) {
        try {
            return ResponseEntity.ok(receiptScanService.scan(receiptRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }

}
