package br.ufpr.tads.receiptscan.mapper;

import br.ufpr.tads.receiptscan.model.ProcessedItem;
import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import br.ufpr.tads.receiptscan.utils.FormatValues;
import br.ufpr.tads.receiptscan.utils.HtmlUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.ufpr.tads.receiptscan.utils.HtmlUtils.getElement;

@Component
public class ItemMapper {

    private static final String CLASS_NAME = ".txtTit2";
    private static final String CLASS_CODE = ".RCod";
    private static final String CLASS_QUANTITY = ".Rqtd";
    private static final String CLASS_UNIT = ".RUN";
    private static final String CLASS_UNIT_VALUE = ".RvlUnit";
    private static final String CLASS_TOTAL_VALUE = ".valor";
    private static final String TBODY_ITEMS = "table#tabResult > tbody";

    public void mapItems(ProcessedReceipt receipt, Document document) {
        Elements items = HtmlUtils.getElements(document, TBODY_ITEMS);
        List<ProcessedItem> itemList = items.stream().map(item -> mapItem(receipt, item)).toList();
        receipt.setItems(summarizeItems(itemList));
    }

    private ProcessedItem mapItem(ProcessedReceipt receipt, Element elementItem) {
        ProcessedItem processedItem = new ProcessedItem();
        processedItem.setProductDescription(getElement(elementItem, CLASS_NAME).text());
        processedItem.setProductCode(extractItemCode(getElement(elementItem, CLASS_CODE).text()));
        processedItem.setQuantity(FormatValues.formatDecimalValue(getElement(elementItem, CLASS_QUANTITY).childNodes().get(2).toString()));
        processedItem.setUnit(getElement(elementItem, CLASS_UNIT).childNodes().get(2).toString().trim());
        processedItem.setUnitValue(FormatValues.formatDecimalValue(getElement(elementItem, CLASS_UNIT_VALUE).childNodes().get(2).toString()));
        processedItem.setTotalValue(FormatValues.formatDecimalValue(getElement(elementItem, CLASS_TOTAL_VALUE).text()));
        processedItem.setReceipt(receipt);
        return processedItem;
    }

    private List<ProcessedItem> summarizeItems(List<ProcessedItem> processedItems) {
        Map<String, ProcessedItem> summarized = processedItems.stream()
                .collect(Collectors.toMap(
                        ProcessedItem::getProductCode,
                        item -> {
                            ProcessedItem copy = new ProcessedItem();
                            copy.setProductCode(item.getProductCode());
                            copy.setProductDescription(item.getProductDescription());
                            copy.setQuantity(item.getQuantity());
                            copy.setUnit(item.getUnit());
                            copy.setUnitValue(item.getUnitValue());
                            copy.setTotalValue(item.getTotalValue());
                            copy.setReceipt(item.getReceipt());
                            return copy;
                        },
                        (item1, item2) -> {
                            item1.setQuantity(item1.getQuantity().add(item2.getQuantity()));
                            item1.setTotalValue(item1.getTotalValue().add(item2.getTotalValue()));
                            return item1;
                        }
                ));
        return new ArrayList<>(summarized.values());
    }

    private String extractItemCode(String codeString) {
        return codeString.replaceAll("[()]", "").split(" ")[1];
    }

}

