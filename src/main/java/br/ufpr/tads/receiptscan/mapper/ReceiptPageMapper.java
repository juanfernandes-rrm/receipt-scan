package br.ufpr.tads.receiptscan.mapper;

import br.ufpr.tads.receiptscan.model.PaymentMethod;
import br.ufpr.tads.receiptscan.model.Receipt;
import br.ufpr.tads.receiptscan.utils.FormatValues;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static br.ufpr.tads.receiptscan.utils.HtmlUtils.getElement;


@Component
public class ReceiptPageMapper {

    private static final String SECTION_CONTENT = "div#conteudo";
    public static final String SECTION_TOTAL_RECEIPT = SECTION_CONTENT + " > div#totalNota";
    private static final String SPAN_ACCESS_KEY = "div > div > ul > li > span";
    public static final String TOTAL_RECEIPT_TOTAL_ITEMS = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:contains(Qtd. total de itens:) > span";
    public static final String TOTAL_RECEIPT_TOTAL_VALUE = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:contains(Valor total R$:) > span";
    public static final String TOTAL_RECEIPT_DISCOUNT = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:contains(Descontos R$:) > span";
    public static final String TOTAL_RECEIPT_VALUE_TO_PAY = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:contains(Valor a pagar R$:) > span";

    public static final String TOTAL_RECEIPT_PAYMENT_METHOD = SECTION_TOTAL_RECEIPT + " > div#linhaForma:contains(Forma de pagamento:) + div#linhaTotal > label";
    public static final String TOTAL_RECEIPT_VALUE_PAID = SECTION_TOTAL_RECEIPT + " > div#linhaForma:contains(Valor pago R$:) + div#linhaTotal > span";
    public static final String TOTAL_RECEIPT_TAX = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:contains(Informação dos Tributos Totais Incidentes (Lei Federal 12.741/2012) R$) > span";

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
        var totalValue = formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_TOTAL_VALUE).text());
        var valueToPay = formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_VALUE_TO_PAY).text());

        receipt.setTotalItems(Integer.parseInt(document.select(TOTAL_RECEIPT_TOTAL_ITEMS).text()));
        receipt.setDiscount(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_DISCOUNT).text()));
        receipt.setValueToPay(valueToPay);
        receipt.setTotalValue(totalValue.equals(BigDecimal.ZERO) ? valueToPay : totalValue);
        receipt.setValuePaid(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_VALUE_PAID).text()));
        receipt.setPaymentMethod(PaymentMethod.fromValue(document.select(TOTAL_RECEIPT_PAYMENT_METHOD).text()));
        receipt.setTax(formatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_TAX).text()));
    }

    private void mapAccessKey(Receipt receipt, Document document) {
        receipt.setAccessKey(getElement(document, SPAN_ACCESS_KEY).text().replaceAll(" ", ""));
    }
}
