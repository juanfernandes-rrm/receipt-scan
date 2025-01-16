package br.ufpr.tads.receiptscan.service;

import br.ufpr.tads.receiptscan.dto.request.ReceiptRequestDTO;
import br.ufpr.tads.receiptscan.dto.response.ReceiptResponseDTO;
import br.ufpr.tads.receiptscan.dto.response.ReceiptSummaryResponseDTO;
import br.ufpr.tads.receiptscan.dto.response.UserStatistics;
import br.ufpr.tads.receiptscan.mapper.ReceiptMapper;
import br.ufpr.tads.receiptscan.model.Receipt;
import br.ufpr.tads.receiptscan.repository.ReceiptRepository;
import br.ufpr.tads.receiptscan.service.messaging.ReceiptPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public Slice<ReceiptSummaryResponseDTO> getScannedReceipts(UUID keycloakId, Pageable pageable) {
        Page<Receipt> receiptPage = receiptRepository.findByScannedBy(keycloakId, pageable);
        List<ReceiptSummaryResponseDTO> receiptSummaryResponseDTOS = new ArrayList<>();
        receiptPage.getContent().forEach(receipt -> receiptSummaryResponseDTOS.add(receiptMapper.mapToReceiptSummary(receipt)));
        return new SliceImpl<>(receiptSummaryResponseDTOS, pageable, receiptPage.hasNext());
    }

    public Slice<UserStatistics> getUserStatistics(Pageable pageable) {
        return receiptRepository.findUserStatistics(pageable);
    }
}
