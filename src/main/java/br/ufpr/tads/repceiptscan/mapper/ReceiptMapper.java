package br.ufpr.tads.repceiptscan.mapper;

import br.ufpr.tads.repceiptscan.model.*;
import br.ufpr.tads.repceiptscan.utils.FormatValues;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class ReceiptMapper {

    private FormatValues formatValues = new FormatValues();

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

    public Receipt map(Document document) {
        Receipt receipt = new Receipt();

        Elements itens = document.select(TBODY_ITEMS).first().children();
        mapItems(receipt, itens);
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
        item.setName(elementItem.select(CLASS_NAME).first().text());
        item.setCode(extractItemCode(elementItem.select(CLASS_CODE).first().text()));
        item.setAmount(formatValues.formatDecimalValue(elementItem.select(CLASS_AMOUNT).first().childNodes().get(2).toString()));
        item.setUnit(elementItem.select(CLASS_UNIT).first().childNodes().get(2).toString());
        item.setUnitValue(formatValues.formatDecimalValue(elementItem.select(CLASS_UNIT_VALUE).first().childNodes().get(2).toString()));
        item.setTotalValue(formatValues.formatDecimalValue(elementItem.select(CLASS_TOTAL_VALUE).first().text()));
        return item;
    }

    private void mapStore(Receipt receipt, Document document) {
        Store store = new Store();
        store.setName(document.select(STORE_NAME).text());
        store.setCNPJ(document.select(STORE_CNPJ).text().replace("CNPJ: ", ""));
        store.setAddress(extractAddress(document.select(STORE_ADDRESS).text()));

        receipt.setStore(store);
    }

    private void mapTotalReceipt(Receipt receipt, Document document) {
        receipt.setTotalItems(Integer.parseInt(document.select(TOTAL_RECEIPT_TOTAL_ITEMS).text()));
        receipt.setValuePaid(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_VALUE_PAID).text()));
        receipt.setTotalValue(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_TOTAL_VALUE).text()));
        receipt.setPaymentMethod(document.select(TOTAL_RECEIPT_PAYMENT_METHOD).text());
        receipt.setTax(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_TAX).text()));
    }

    private void mapGeneralInformation(Receipt receipt, Document document) {
        generalInformation generalInformation = new generalInformation();
        generalInformation.setNumber(document.select(INFORMATION_NUMBER).first().nextSibling().toString());
        generalInformation.setSeries(document.select(INFORMATION_SERIES).first().nextSibling().toString());
        generalInformation.setIssuance(extractIssuance(document.select(INFORMATION_ISSUANCE).first().nextSibling().toString()));
        generalInformation.setAuthorizationProtocol(extractAuthorizationProtocol(document.select(INFORMATION_PROTOCOL).first().nextSibling().toString()));
        receipt.setGeneralInformation(generalInformation);
    }

    private void mapAccessKey(Receipt receipt, Document document) {
        receipt.setAccessKey(document.select(SPAN_ACCESS_KEY).first().text());
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
        address.setCity(split[4]);
        address.setState(split[5]);
        return address;
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

}
