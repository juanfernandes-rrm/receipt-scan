package br.ufpr.tads.repceiptscan.controller;

import br.ufpr.tads.repceiptscan.dto.request.ReceiptRequestDTO;
import br.ufpr.tads.repceiptscan.service.ReceiptScanService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/scan")
public class ReceiptScanController {

    @Autowired
    private ReceiptScanService receiptScanService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> scan(@RequestBody @Valid ReceiptRequestDTO receiptRequestDTO) {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("User: " + jwt.getSubject());
            return ResponseEntity.ok(receiptScanService.scan(receiptRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }

}
