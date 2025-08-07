package br.ufpr.tads.receiptscan.mapper;

import br.ufpr.tads.receiptscan.dto.response.ProcessedItemDTO;
import br.ufpr.tads.receiptscan.dto.response.ProcessedReceiptResponseDTO;
import br.ufpr.tads.receiptscan.model.ProcessedItem;
import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReceiptMapper {

    public ProcessedReceiptResponseDTO map(ProcessedReceipt receipt) {
        ProcessedReceiptResponseDTO responseDTO = new ProcessedReceiptResponseDTO();

        responseDTO.setId(receipt.getId());
        responseDTO.setScannedAt(receipt.getScannedAt());
        responseDTO.setStoreName(receipt.getStoreName());
        responseDTO.setStoreCnpj(receipt.getStoreCnpj());
        responseDTO.setStoreAddress(receipt.getStoreAddress());
        responseDTO.setItems(mapItems(receipt.getItems()));
        responseDTO.setTotalItems(receipt.getTotalItems());
        responseDTO.setTotalValue(receipt.getTotalValue());
        responseDTO.setDiscount(receipt.getDiscount());
        responseDTO.setValueToPay(receipt.getValueToPay());
        responseDTO.setPaymentMethod(receipt.getPaymentMethod().name());
        responseDTO.setValuePaid(receipt.getValuePaid());
        responseDTO.setTax(receipt.getTax());
        responseDTO.setNumber(receipt.getNumber());
        responseDTO.setSeries(receipt.getSeries());
        responseDTO.setIssuer(receipt.getIssuer());
        responseDTO.setIssuanceDate(receipt.getIssuanceDate());
        responseDTO.setAuthorizationCode(receipt.getAuthorizationCode());
        responseDTO.setAuthorizationDate(receipt.getAuthorizationDate());
        responseDTO.setAccessKey(receipt.getAccessKey());
        responseDTO.setConsumerCpf(receipt.getConsumerCpf());
        responseDTO.setConsumerName(receipt.getConsumerName());
        return responseDTO;
    }

    private List<ProcessedItemDTO> mapItems(List<ProcessedItem> items) {
        List<ProcessedItemDTO> processedItemDTOS = new ArrayList<>();
        items.forEach(item -> {
            ProcessedItemDTO processedItemDTO = new ProcessedItemDTO();
            processedItemDTO.setId(item.getId());
            processedItemDTO.setProductDescription(item.getProductDescription());
            processedItemDTO.setProductCode(item.getProductCode());
            processedItemDTO.setQuantity(item.getQuantity());
            processedItemDTO.setUnit(item.getUnit());
            processedItemDTO.setUnitValue(item.getUnitValue());
            processedItemDTO.setTotalValue(item.getTotalValue());
            processedItemDTOS.add(processedItemDTO);
        });
        return processedItemDTOS;
    }

}
