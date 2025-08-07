package br.ufpr.tads.receiptscan.service;


import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import br.ufpr.tads.receiptscan.repository.ProcessedItemRepository;
import br.ufpr.tads.receiptscan.repository.ProcessedReceiptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReceiptProcessingService {

    @Autowired
    private ReceiptScrapingService receiptScrapingService;

    @Autowired
    private ReceiptValidationService receiptValidationService;

    @Autowired
    private ProcessedItemRepository processedItemRepository;

    @Autowired
    private ProcessedReceiptRepository processedReceiptRepository;

    public ProcessedReceipt processReceipt(String url) {
        log.info("Processing receipt {}", url);
        receiptValidationService.validateReceipt(url);

        ProcessedReceipt receipt = receiptScrapingService.scrapeReceipt(url);
        ProcessedReceipt scannedReceipt = processedReceiptRepository.save(receipt);

        log.info("Receipt {} successfully scanned", url);
        return scannedReceipt;
    }
}

