package br.ufpr.tads.repceiptscan.service;


import br.ufpr.tads.repceiptscan.model.Receipt;
import br.ufpr.tads.repceiptscan.repository.AuthorizationProtocolRepository;
import br.ufpr.tads.repceiptscan.repository.GeneralInformationRepository;
import br.ufpr.tads.repceiptscan.repository.IssuanceRepository;
import br.ufpr.tads.repceiptscan.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private GeneralInformationRepository generalInformationRepository;

    @Autowired
    private AuthorizationProtocolRepository authorizationProtocolRepository;

    @Autowired
    private IssuanceRepository issuanceRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    public Receipt processReceipt(String url) {
        receiptValidationService.validateReceipt(url);

        Receipt receipt = receiptScrapingService.scrapeReceipt(url);

        storeService.saveOrGetStore(receipt);
        authorizationProtocolRepository.save(receipt.getGeneralInformation().getAuthorizationProtocol());
        issuanceRepository.save(receipt.getGeneralInformation().getIssuance());
        generalInformationRepository.save(receipt.getGeneralInformation());
        itemService.saveOrGetItemDetails(receipt);

        return receiptRepository.save(receipt);
    }
}

