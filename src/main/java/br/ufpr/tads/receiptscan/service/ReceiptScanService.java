package br.ufpr.tads.receiptscan.service;

import br.ufpr.tads.receiptscan.dto.request.ReceiptRequestDTO;
import br.ufpr.tads.receiptscan.dto.response.ReceiptResponseDTO;
import br.ufpr.tads.receiptscan.mapper.ReceiptMapper;
import br.ufpr.tads.receiptscan.model.Receipt;
import br.ufpr.tads.receiptscan.repository.ReceiptRepository;
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
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptMapper receiptMapper;

    public ReceiptResponseDTO scan(ReceiptRequestDTO receiptRequestDTO, UUID user) {
        Receipt receipt = receiptProcessingService.processReceipt(receiptRequestDTO.getUrl(), user);
        receiptPublisher.publishReceipt(receipt);
        return receiptMapper.map(receipt);
    }

}
