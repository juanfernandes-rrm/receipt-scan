package br.ufpr.tads.repceiptscan.service;

import br.ufpr.tads.repceiptscan.dto.request.ReceiptRequestDTO;
import br.ufpr.tads.repceiptscan.dto.response.ReceiptResponseDTO;
import br.ufpr.tads.repceiptscan.mapper.ReceiptMapper;
import br.ufpr.tads.repceiptscan.mapper.ReceiptPageMapper;
import br.ufpr.tads.repceiptscan.service.messaging.RabbitPublisher;
import br.ufpr.tads.repceiptscan.model.Receipt;
import br.ufpr.tads.repceiptscan.model.Store;
import br.ufpr.tads.repceiptscan.repository.*;
import br.ufpr.tads.repceiptscan.utils.HTMLReader;
import br.ufpr.tads.repceiptscan.utils.PageConnectionFactory;
import br.ufpr.tads.repceiptscan.utils.PageValidate;
import br.ufpr.tads.repceiptscan.utils.ReceiptURLValidate;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;

@Service
public class ReceiptScanService {

    @Autowired
    private ReceiptURLValidate receiptURLValidate;

    @Autowired
    private PageConnectionFactory pageConnectionFactory;

    @Autowired
    private PageValidate pageValidate;

    @Autowired
    private HTMLReader htmlReader;

    @Autowired
    private ReceiptPageMapper receiptPageMapper;

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private RabbitPublisher rabbitPublisher;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private GeneralInformationRepository generalInformationRepository;

    @Autowired
    private AuthorizationProtocolRepository authorizationProtocolRepository;

    @Autowired
    private IssuanceRepository issuanceRepository;

    public ReceiptResponseDTO scan(ReceiptRequestDTO receiptRequestDTO) {
        receiptURLValidate.validate(receiptRequestDTO.getUrl());

        HttpURLConnection connection = pageConnectionFactory.getConnection(receiptRequestDTO.getUrl());
        Document document = htmlReader.getDocument(connection);

        pageValidate.validatePage(document);

        Receipt receipt = saveReceipt(receiptPageMapper.map(document));
        ReceiptResponseDTO responseDTO = receiptMapper.map(receipt);
        rabbitPublisher.publish(responseDTO);
        return responseDTO;
    }

    private Receipt saveReceipt(Receipt receipt){
        Store store = receipt.getStore();
        addressRepository.save(store.getAddress());
        storeRepository.save(store);
        receipt.getItems().forEach(item -> itemRepository.save(item));
        authorizationProtocolRepository.save(receipt.getGeneralInformation().getAuthorizationProtocol());
        issuanceRepository.save(receipt.getGeneralInformation().getIssuance());
        generalInformationRepository.save(receipt.getGeneralInformation());
        return receiptRepository.save(receipt);
    }

}
