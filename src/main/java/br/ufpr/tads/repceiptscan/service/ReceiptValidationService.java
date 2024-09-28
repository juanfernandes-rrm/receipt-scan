package br.ufpr.tads.repceiptscan.service;

import br.ufpr.tads.repceiptscan.repository.ReceiptRepository;
import br.ufpr.tads.repceiptscan.utils.ReceiptURLValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptValidationService {

    @Autowired
    private ReceiptURLValidate receiptURLValidate;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private AccessKeyExtractor accessKeyExtractor;

    public void validateReceipt(String url) {
        receiptURLValidate.validate(url);
        validateReceiptWasNotScanned(url);
    }

    private void validateReceiptWasNotScanned(String url) {
        String accessKey = accessKeyExtractor.extract(url);
        if (accessKey != null && receiptRepository.findByAccessKey(accessKey).isPresent()) {
            throw new RuntimeException("Receipt already scanned");
        }
    }
}