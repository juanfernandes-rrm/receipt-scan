package br.ufpr.tads.repceiptscan.mapper;

import br.ufpr.tads.repceiptscan.model.*;
import br.ufpr.tads.repceiptscan.utils.FormatValues;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReceiptPageMapper {

    @Autowired
    private FormatValues formatValues;

    private static final String SECTION_CONTENT = "div#conteudo";
    private static final String SECTION_STORE = SECTION_CONTENT + " > div:nth-child(2)";
    private static final String TBODY_ITEMS = "table#tabResult > tbody";
    public static final String SECTION_TOTAL_RECEIPT = SECTION_CONTENT + " > div#totalNota";
    public static final String SECTION_INFORMATION = "div#infos";
    private static final String SPAN_ACCESS_KEY = "div > div > ul > li > span";

    private static final String STORE_NAME = SECTION_STORE + " > div:nth-child(1)";
    private static final String STORE_CNPJ = SECTION_STORE + " > div:nth-child(2)";
    private static final String STORE_ADDRESS = SECTION_STORE + " > div:nth-child(3)";

    private static final String CLASS_NAME = ".txtTit2";
    private static final String CLASS_CODE = ".RCod";
    private static final String CLASS_AMOUNT = ".Rqtd";
    private static final String CLASS_UNIT = ".RUN";
    private static final String CLASS_UNIT_VALUE = ".RvlUnit";
    private static final String CLASS_TOTAL_VALUE = ".valor";

    public static final String TOTAL_RECEIPT_TOTAL_ITEMS = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:nth-child(1) > span";
    public static final String TOTAL_RECEIPT_TOTAL_VALUE = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:nth-child(2) > span";
    public static final String TOTAL_RECEIPT_PAYMENT_METHOD = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:nth-child(4) > label";
    public static final String TOTAL_RECEIPT_VALUE_PAID = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:nth-child(4) > span";
    public static final String TOTAL_RECEIPT_TAX = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:nth-child(5) > span";

    public static final String INFORMATION_NUMBER = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(4)";
    public static final String INFORMATION_SERIES = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(5)";
    public static final String INFORMATION_ISSUANCE = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(6)";
    public static final String INFORMATION_PROTOCOL = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(9)";

    public static final String REGEX_TO_REMOVE_CNPJ_MASK = "[^0-9]";

    public Receipt map(Document document) {
        Receipt receipt = new Receipt();

        Elements items = getElements(document, TBODY_ITEMS);
        mapItems(receipt, items);
        mapStore(receipt, document);
        mapTotalReceipt(receipt, document);
        mapGeneralInformation(receipt, document);
        mapAccessKey(receipt, document);

        return receipt;
    }

    private void mapItems(Receipt receipt, Elements itens) {
        List<Item> itemList = itens.stream().map(this::mapItem).toList();
        receipt.setItems(itemList);
    }

    private Item mapItem(Element elementItem) {
        Item item = new Item();
        item.setName(getElement(elementItem, CLASS_NAME).text());
        item.setCode(extractItemCode(getElement(elementItem, CLASS_CODE).text()));
        item.setAmount(formatValues.formatDecimalValue(getElement(elementItem, CLASS_AMOUNT).childNodes().get(2).toString()));
        item.setUnit(getElement(elementItem, CLASS_UNIT).childNodes().get(2).toString().trim());
        item.setUnitValue(formatValues.formatDecimalValue(getElement(elementItem, CLASS_UNIT_VALUE).childNodes().get(2).toString()));
        item.setTotalValue(formatValues.formatDecimalValue(getElement(elementItem, CLASS_TOTAL_VALUE).text()));
        return item;
    }

    private void mapStore(Receipt receipt, Document document) {
        Store store = new Store();
        store.setName(document.select(STORE_NAME).text());
        store.setCNPJ(document.select(STORE_CNPJ).text().replace("CNPJ: ", "").replaceAll(REGEX_TO_REMOVE_CNPJ_MASK, ""));
        store.setAddress(extractAddress(document.select(STORE_ADDRESS).text()));

        receipt.setStore(store);
    }

    private void mapTotalReceipt(Receipt receipt, Document document) {
        receipt.setTotalItems(Integer.parseInt(document.select(TOTAL_RECEIPT_TOTAL_ITEMS).text()));
        receipt.setValuePaid(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_VALUE_PAID).text()));
        receipt.setTotalValue(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_TOTAL_VALUE).text()));
        receipt.setPaymentMethod(PaymentMethod.fromValue(document.select(TOTAL_RECEIPT_PAYMENT_METHOD).text()));
        receipt.setTax(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_TAX).text()));
    }

    private void mapGeneralInformation(Receipt receipt, Document document) {
        GeneralInformation generalInformation = new GeneralInformation();
        generalInformation.setNumber(getNextTextNodeFrom(document, INFORMATION_NUMBER));
        generalInformation.setSeries(getNextTextNodeFrom(document, INFORMATION_SERIES));
        generalInformation.setIssuance(extractIssuance(getNextTextNodeFrom(document, INFORMATION_ISSUANCE)));
        generalInformation.setAuthorizationProtocol(extractAuthorizationProtocol(getNextTextNodeFrom(document, INFORMATION_PROTOCOL)));
        receipt.setGeneralInformation(generalInformation);
    }

    private void mapAccessKey(Receipt receipt, Document document) {
        receipt.setAccessKey(getElement(document, SPAN_ACCESS_KEY).text());
    }

    private String extractItemCode(String codeString) {
        return codeString.replaceAll("[()]", "").split(" ")[1];
    }

    private Address extractAddress(String addressString) {
        Address address = new Address();
        String[] split = addressString.split(", ");
        address.setStreet(split[0]);
        address.setNumber(split[1]);
        address.setNeighborhood(split[3]);
        address.setCity(extractCity(split[4], split[5]));
        return address;
    }

    private City extractCity(String cityName, String stateName) {
        City city = new City();
        city.setName(cityName);
        city.setState(StateEnum.valueOf(stateName));
        return city;
    }

    private Issuance extractIssuance(String issuanceString) {
        Issuance issuance = new Issuance();
        String[] split = issuanceString.split(" ");
        issuance.setDate(formatValues.formatDateTime(split[0] + split[1]));
        issuance.setIssuer(split[4]);
        return issuance;
    }

    private AuthorizationProtocol extractAuthorizationProtocol(String protocol) {
        AuthorizationProtocol authorizationProtocol = new AuthorizationProtocol();
        String[] split = protocol.split(" ");
        authorizationProtocol.setCode(split[0]);
        authorizationProtocol.setDate(formatValues.formatDateTime(split[1] + split[2]));
        return authorizationProtocol;
    }

    private Element getElement(Element element, String select) {
        return Optional.ofNullable(element.select(select).first())
                .orElseThrow(() -> new RuntimeException("Element not found"));
    }

    private Element getElement(Document document, String select) {
        return Optional.ofNullable(document.select(select).first())
                .orElseThrow(() -> new RuntimeException("Element not found"));
    }

    private Elements getElements(Document document, String select) {
        return Optional.ofNullable(document.select(select).first())
                .map(Element::children)
                .orElseThrow(() -> new RuntimeException("Element not found or has no children"));
    }

    private String getNextTextNodeFrom(Document document, String select) {
        Element element = getElement(document, select);
        return Optional.ofNullable(element.nextSibling())
                .map(text -> text.toString().trim())
                .orElseThrow(() -> new RuntimeException("Text node not found"));
    }

}
