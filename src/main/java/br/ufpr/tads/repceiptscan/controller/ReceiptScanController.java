package br.ufpr.tads.repceiptscan.controller;

import br.ufpr.tads.repceiptscan.dto.request.ReceiptRequestDTO;
import br.ufpr.tads.repceiptscan.service.ReceiptScanService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

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
        try {
            return ResponseEntity.ok(receiptScanService.scan(receiptRequestDTO, getUser()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }

    @GetMapping("/receipts/{keycloakId}")
    public ResponseEntity<?> getScannedReceipts(@PathVariable UUID keycloakId,
                                                @RequestParam("page") int page, @RequestParam("size") int size,
                                                @RequestParam("sortDirection") Sort.Direction sortDirection, @RequestParam("sortBy") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return ResponseEntity.ok(receiptScanService.getScannedReceipts(keycloakId, pageable));
    }

    private UUID getUser() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User: {}", jwt.getClaimAsString("preferred_username"));
        return UUID.fromString(jwt.getSubject());
    }

}
