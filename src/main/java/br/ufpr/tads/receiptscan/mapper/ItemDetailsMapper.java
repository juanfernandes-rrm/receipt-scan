package br.ufpr.tads.receiptscan.mapper;

import br.ufpr.tads.receiptscan.model.Item;
import br.ufpr.tads.receiptscan.model.ItemDetails;
import br.ufpr.tads.receiptscan.utils.FormatValues;
import br.ufpr.tads.receiptscan.utils.HtmlUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.ufpr.tads.receiptscan.utils.HtmlUtils.getElement;

@Component
public class ItemDetailsMapper {

    private static final String CLASS_NAME = ".txtTit2";
    private static final String CLASS_CODE = ".RCod";
    private static final String CLASS_AMOUNT = ".Rqtd";
    private static final String CLASS_UNIT = ".RUN";
    private static final String CLASS_UNIT_VALUE = ".RvlUnit";
    private static final String CLASS_TOTAL_VALUE = ".valor";
    private static final String TBODY_ITEMS = "table#tabResult > tbody";

    public List<ItemDetails> mapItems(Document document) {
        Elements items = HtmlUtils.getElements(document, TBODY_ITEMS);
        List<ItemDetails> itemList = items.stream().map(this::mapItem).toList();
        return summarizeItems(itemList);
    }

    private ItemDetails mapItem(Element elementItem) {
        ItemDetails itemDetails = new ItemDetails();
        Item item = new Item();
        item.setName(getElement(elementItem, CLASS_NAME).text());
        item.setCode(extractItemCode(getElement(elementItem, CLASS_CODE).text()));
        itemDetails.setItem(item);
        itemDetails.setAmount(FormatValues.formatDecimalValue(getElement(elementItem, CLASS_AMOUNT).childNodes().get(2).toString()));
        itemDetails.setUnit(getElement(elementItem, CLASS_UNIT).childNodes().get(2).toString().trim());
        itemDetails.setUnitValue(FormatValues.formatDecimalValue(getElement(elementItem, CLASS_UNIT_VALUE).childNodes().get(2).toString()));
        itemDetails.setTotalValue(FormatValues.formatDecimalValue(getElement(elementItem, CLASS_TOTAL_VALUE).text()));
        return itemDetails;
    }

    private List<ItemDetails> summarizeItems(List<ItemDetails> itemDetails) {
        Map<Item, ItemDetails> summarizedMap = itemDetails.stream()
                .collect(Collectors.toMap(
                        ItemDetails::getItem,
                        item -> {
                            ItemDetails copy = new ItemDetails();
                            copy.setItem(item.getItem());
                            copy.setAmount(item.getAmount());
                            copy.setTotalValue(item.getTotalValue());
                            copy.setUnit(item.getUnit());
                            copy.setUnitValue(item.getUnitValue());
                            return copy;
                        },
                        (itemDetails1, itemDetails2) -> {
                            itemDetails1.setAmount(itemDetails1.getAmount().add(itemDetails2.getAmount()));
                            itemDetails1.setTotalValue(itemDetails1.getTotalValue().add(itemDetails2.getTotalValue()));
                            return itemDetails1;
                        }
                ));

        return summarizedMap.values().stream().toList();
    }

    private String extractItemCode(String codeString) {
        return codeString.replaceAll("[()]", "").split(" ")[1];
    }

}

