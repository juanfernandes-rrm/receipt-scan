package br.ufpr.tads.receiptscan.service;

import br.ufpr.tads.receiptscan.model.ItemDetails;
import br.ufpr.tads.receiptscan.model.Receipt;
import br.ufpr.tads.receiptscan.repository.ItemDetailsRepository;
import br.ufpr.tads.receiptscan.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemDetailsRepository itemDetailsRepository;

    public void saveOrGetItemDetails(Receipt receipt) {
        receipt.getItemDetails().forEach(itemDetail -> itemDetail.setReceipt(receipt));
        saveItems(receipt.getItemDetails());
    }

    private void saveItems(List<ItemDetails> itemDetails) {
        itemDetails.forEach(itemDetail -> {
            itemRepository.findByCode(itemDetail.getItem().getCode())
                    .ifPresentOrElse(itemDetail::setItem,
                            () -> itemRepository.save(itemDetail.getItem()));
        });
    }
}
