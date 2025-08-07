package br.ufpr.tads.receiptscan.service;

import br.ufpr.tads.receiptscan.dto.request.ReceiptRequestDTO;
import br.ufpr.tads.receiptscan.dto.response.ProcessedReceiptResponseDTO;
import br.ufpr.tads.receiptscan.mapper.ReceiptMapper;
import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import br.ufpr.tads.receiptscan.repository.ProcessedReceiptRepository;
import br.ufpr.tads.receiptscan.service.messaging.ReceiptPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReceiptScanService {

    @Autowired
    private ReceiptProcessingService receiptProcessingService;

    @Autowired
    private ReceiptPublisher receiptPublisher;

    @Autowired
    private ProcessedReceiptRepository processedReceiptRepository;

    @Autowired
    private ReceiptMapper receiptMapper;

    public ProcessedReceiptResponseDTO scan(ReceiptRequestDTO receiptRequestDTO, UUID userId) {
        ProcessedReceipt processedReceipt = receiptProcessingService.processReceipt(receiptRequestDTO.getUrl());
        //TODO: Implement user association with receipt
        receiptPublisher.publishReceipt(processedReceipt);
        return receiptMapper.map(processedReceipt);
    }

}
