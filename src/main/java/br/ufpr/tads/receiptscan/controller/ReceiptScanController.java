package br.ufpr.tads.receiptscan.controller;

import br.ufpr.tads.receiptscan.dto.request.ReceiptRequestDTO;
import br.ufpr.tads.receiptscan.service.ReceiptScanService;
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

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/scan")
public class ReceiptScanController {

    @Autowired
    private ReceiptScanService receiptScanService;

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping
    public ResponseEntity<?> scan(@RequestBody @Valid ReceiptRequestDTO receiptRequestDTO) {
        log.info("Scanning receipt for user with keycloakId {}", getUser());
        return ResponseEntity.ok(receiptScanService.scan(receiptRequestDTO, getUser()));
    }

    private UUID getUser() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User: {}", jwt.getClaimAsString("preferred_username"));
        return UUID.fromString(jwt.getSubject());
    }

}
