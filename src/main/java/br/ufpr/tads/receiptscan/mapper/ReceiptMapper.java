package br.ufpr.tads.receiptscan.mapper;

import br.ufpr.tads.receiptscan.dto.response.*;
import br.ufpr.tads.receiptscan.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReceiptMapper {

    public ReceiptResponseDTO map(Receipt receipt) {
        ReceiptResponseDTO responseDTO = new ReceiptResponseDTO();

        responseDTO.setScannedBy(receipt.getScannedBy());
        responseDTO.setScannedAt(receipt.getScannedAt());
        responseDTO.setStore(mapStore(receipt.getStore()));
        responseDTO.setItems(mapItems(receipt.getItemDetails()));
        responseDTO.setTotalItems(receipt.getTotalItems());
        responseDTO.setTotalValue(receipt.getTotalValue());
        responseDTO.setDiscount(receipt.getDiscount());
        responseDTO.setValueToPay(receipt.getValueToPay());
        responseDTO.setPaymentMethod(receipt.getPaymentMethod().name());
        responseDTO.setValuePaid(receipt.getValuePaid());
        responseDTO.setTax(receipt.getTax());
        responseDTO.setGeneralInformation(mapGeneralInformation(receipt.getGeneralInformation()));
        responseDTO.setAccessKey(receipt.getAccessKey());
        return responseDTO;
    }

    public ReceiptSummaryResponseDTO mapToReceiptSummary(Receipt receipt) {
        ReceiptSummaryResponseDTO responseDTO = new ReceiptSummaryResponseDTO();

        responseDTO.setScannedAt(receipt.getScannedAt());
        responseDTO.setItems(mapItems(receipt.getItemDetails()));
        responseDTO.setTotalValue(receipt.getTotalValue());
        return responseDTO;
    }

    private StoreDTO mapStore(Store store) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(store.getId());
        storeDTO.setName(store.getName());
        storeDTO.setCNPJ(store.getCNPJ());
        storeDTO.setAddress(mapAddress(store.getAddress()));
        return storeDTO;
    }

    private AddressDTO mapAddress(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(address.getStreet());
        addressDTO.setNumber(address.getNumber());
        addressDTO.setNeighborhood(address.getNeighborhood());
        addressDTO.setCity(address.getCity().getName());
        addressDTO.setState(address.getCity().getState().name());
        return addressDTO;
    }

    private List<ItemDTO> mapItems(List<ItemDetails> items) {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        items.forEach(item -> {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setName(item.getItem().getName());
            itemDTO.setCode(item.getItem().getCode());
            itemDTO.setAmount(item.getAmount());
            itemDTO.setUnit(item.getUnit());
            itemDTO.setUnitValue(item.getUnitValue());
            itemDTO.setTotalValue(item.getTotalValue());
            itemDTOS.add(itemDTO);
        });
        return itemDTOS;
    }

    private GeneralInformationDTO mapGeneralInformation(GeneralInformation generalInformation) {
        GeneralInformationDTO generalInformationDTO = new GeneralInformationDTO();
        generalInformationDTO.setNumber(generalInformation.getNumber());
        generalInformationDTO.setSeries(generalInformation.getSeries());
        generalInformationDTO.setIssuance(mapIssuance(generalInformation.getIssuance()));
        generalInformationDTO.setAuthorizationProtocol(mapAuthorizationProtocol(generalInformation.getAuthorizationProtocol()));
        return generalInformationDTO;
    }


    private IssuanceDTO mapIssuance(Issuance issuance) {
        IssuanceDTO issuanceDTO = new IssuanceDTO();
        issuanceDTO.setDate(issuance.getDate());
        issuanceDTO.setIssuer(issuance.getIssuer());
        return issuanceDTO;
    }

    private AuthorizationProtocolDTO mapAuthorizationProtocol(AuthorizationProtocol authorizationProtocol) {
        AuthorizationProtocolDTO authorizationProtocolDTO = new AuthorizationProtocolDTO();
        authorizationProtocolDTO.setCode(authorizationProtocol.getCode());
        authorizationProtocolDTO.setDate(authorizationProtocol.getDate());
        return authorizationProtocolDTO;
    }

}
