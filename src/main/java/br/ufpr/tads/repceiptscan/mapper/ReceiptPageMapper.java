package br.ufpr.tads.repceiptscan.mapper;

import br.ufpr.tads.repceiptscan.model.PaymentMethod;
import br.ufpr.tads.repceiptscan.model.Receipt;
import br.ufpr.tads.repceiptscan.utils.FormatValues;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.ufpr.tads.repceiptscan.utils.HtmlUtils.getElement;


@Component
public class ReceiptPageMapper {

    private static final String SECTION_CONTENT = "div#conteudo";
    public static final String SECTION_TOTAL_RECEIPT = SECTION_CONTENT + " > div#totalNota";
    private static final String SPAN_ACCESS_KEY = "div > div > ul > li > span";
    public static final String TOTAL_RECEIPT_TOTAL_ITEMS = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:nth-child(1) > span";
    public static final String TOTAL_RECEIPT_TOTAL_VALUE = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:nth-child(2) > span";
    public static final String TOTAL_RECEIPT_PAYMENT_METHOD = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:nth-child(4) > label";
    public static final String TOTAL_RECEIPT_VALUE_PAID = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:nth-child(4) > span";
    public static final String TOTAL_RECEIPT_TAX = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:nth-child(5) > span";

    @Autowired
    private FormatValues formatValues;

    @Autowired
    private ItemDetailsMapper itemDetailsMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private GeneralInformationMapper generalInformationMapper;

    public Receipt map(Document document) {
        Receipt receipt = new Receipt();

        receipt.setItemDetails(itemDetailsMapper.mapItems(document));
        receipt.setStore(storeMapper.mapStore(document));
        receipt.setGeneralInformation(generalInformationMapper.mapGeneralInformation(document));
        mapTotalReceipt(receipt, document);
        mapAccessKey(receipt, document);

        return receipt;
    }

    private void mapTotalReceipt(Receipt receipt, Document document) {
        receipt.setTotalItems(Integer.parseInt(document.select(TOTAL_RECEIPT_TOTAL_ITEMS).text()));
        receipt.setValuePaid(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_VALUE_PAID).text()));
        receipt.setTotalValue(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_TOTAL_VALUE).text()));
        receipt.setPaymentMethod(PaymentMethod.fromValue(document.select(TOTAL_RECEIPT_PAYMENT_METHOD).text()));
        receipt.setTax(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_TAX).text()));
    }

    private void mapAccessKey(Receipt receipt, Document document) {
        receipt.setAccessKey(getElement(document, SPAN_ACCESS_KEY).text().replaceAll(" ", ""));
    }
}
