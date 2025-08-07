package br.ufpr.tads.receiptscan.service;

import br.ufpr.tads.receiptscan.exception.ReceiptAlreadyScannedException;
import br.ufpr.tads.receiptscan.repository.ProcessedReceiptRepository;
import br.ufpr.tads.receiptscan.utils.ReceiptURLValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReceiptValidationService {

    @Autowired
    private ReceiptURLValidate receiptURLValidate;

    @Autowired
    private ProcessedReceiptRepository processedReceiptRepository;

    @Autowired
    private AccessKeyExtractor accessKeyExtractor;

    public void validateReceipt(String url) {
        receiptURLValidate.validate(url);
        validateReceiptWasNotScanned(url);
    }

    private void validateReceiptWasNotScanned(String url) {
        String accessKey = accessKeyExtractor.extract(url);
        if (accessKey != null && processedReceiptRepository.findByAccessKey(accessKey).isPresent()) {
            log.error("Receipt from URL {} has already been scanned", url);
            throw new ReceiptAlreadyScannedException(String.format("Receipt from URL %s has already been scanned", url));
        }
    }
}