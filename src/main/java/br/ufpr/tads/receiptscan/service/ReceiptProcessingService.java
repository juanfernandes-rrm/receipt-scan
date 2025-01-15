package br.ufpr.tads.receiptscan.service;


import br.ufpr.tads.receiptscan.model.Receipt;
import br.ufpr.tads.receiptscan.repository.ReceiptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class ReceiptProcessingService {

    @Autowired
    private ReceiptScrapingService receiptScrapingService;

    @Autowired
    private ReceiptValidationService receiptValidationService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ReceiptRepository receiptRepository;

    public Receipt processReceipt(String url, UUID user) {
        log.info("Processing receipt {} scannedBy {}", url, user);
        receiptValidationService.validateReceipt(url);

        Receipt receipt = receiptScrapingService.scrapeReceipt(url);

        receipt.setScannedBy(user);
        receipt.setScannedAt(LocalDateTime.now());
        storeService.saveOrGetStore(receipt);
        itemService.saveOrGetItemDetails(receipt);

        Receipt scannedReceipt = receiptRepository.save(receipt);
        log.info("Receipt {} successfully scanned", url);
        return scannedReceipt;
    }
}

