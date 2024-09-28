package br.ufpr.tads.repceiptscan.service;

import br.ufpr.tads.repceiptscan.dto.request.ReceiptRequestDTO;
import br.ufpr.tads.repceiptscan.dto.response.ReceiptResponseDTO;
import br.ufpr.tads.repceiptscan.mapper.ReceiptMapper;
import br.ufpr.tads.repceiptscan.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptScanService {

    @Autowired
    private ReceiptProcessingService receiptProcessingService;

    @Autowired
    private ReceiptPublisher receiptPublisher;

    @Autowired
    private ReceiptMapper receiptMapper;

    public ReceiptResponseDTO scan(ReceiptRequestDTO receiptRequestDTO) {
        Receipt receipt = receiptProcessingService.processReceipt(receiptRequestDTO.getUrl());
        receiptPublisher.publishReceipt(receipt);
        return receiptMapper.map(receipt);
    }

}
