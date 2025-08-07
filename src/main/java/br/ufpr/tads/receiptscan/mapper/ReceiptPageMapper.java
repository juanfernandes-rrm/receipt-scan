package br.ufpr.tads.receiptscan.mapper;

import br.ufpr.tads.receiptscan.model.PaymentMethod;
import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import br.ufpr.tads.receiptscan.utils.FormatValues;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static br.ufpr.tads.receiptscan.utils.HtmlUtils.getElement;


@Slf4j
@Component
public class ReceiptPageMapper {

    private static final String SECTION_CONTENT = "div#conteudo";
    private static final String SECTION_TOTAL_RECEIPT = SECTION_CONTENT + " > div#totalNota";
    private static final String SPAN_ACCESS_KEY = "div > div > ul > li > span";
    private static final String TOTAL_RECEIPT_TOTAL_ITEMS = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:contains(Qtd. total de itens:) > span";
    private static final String TOTAL_RECEIPT_TOTAL_VALUE = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:contains(Valor total R$:) > span";
    private static final String TOTAL_RECEIPT_DISCOUNT = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:contains(Descontos R$:) > span";
    private static final String TOTAL_RECEIPT_VALUE_TO_PAY = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:contains(Valor a pagar R$:) > span";
    private static final String TOTAL_RECEIPT_PAYMENT_METHOD = SECTION_TOTAL_RECEIPT + " > div#linhaForma:contains(Forma de pagamento:) + div#linhaTotal > label";
    private static final String TOTAL_RECEIPT_VALUE_PAID = SECTION_TOTAL_RECEIPT + " > div#linhaForma:contains(Valor pago R$:) + div#linhaTotal > span";
    private static final String TOTAL_RECEIPT_TAX = SECTION_TOTAL_RECEIPT + " > div#linhaTotal:contains(Informação dos Tributos Totais Incidentes (Lei Federal 12.741/2012) R$) > span";

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private GeneralInformationMapper generalInformationMapper;

    public ProcessedReceipt map(Document document, String url) {
        log.info("Mapping receipt from URL: {}", url);
        ProcessedReceipt receipt = new ProcessedReceipt();
        receipt.setScannedAt(LocalDateTime.now());

        itemMapper.mapItems(receipt, document);
        storeMapper.mapStore(receipt, document);
        generalInformationMapper.mapGeneralInformation(receipt, document);
        mapTotalReceipt(receipt, document);
        mapAccessKey(receipt, document);

        log.info("Receipt mapped successfully");
        return receipt;
    }

    private void mapTotalReceipt(ProcessedReceipt receipt, Document document) {
        var totalValue = FormatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_TOTAL_VALUE).text());
        var valueToPay = FormatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_VALUE_TO_PAY).text());

        receipt.setTotalItems(Integer.parseInt(document.select(TOTAL_RECEIPT_TOTAL_ITEMS).text()));
        receipt.setDiscount(FormatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_DISCOUNT).text()));
        receipt.setValueToPay(valueToPay);
        receipt.setTotalValue(BigDecimal.ZERO.equals(totalValue) ? valueToPay : totalValue);
        receipt.setValuePaid(FormatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_VALUE_PAID).text()));
        receipt.setPaymentMethod(PaymentMethod.fromValue(document.select(TOTAL_RECEIPT_PAYMENT_METHOD).text()));
        receipt.setTax(FormatValues.formatDecimalValue(document.select(TOTAL_RECEIPT_TAX).text()));
    }

    private void mapAccessKey(ProcessedReceipt receipt, Document document) {
        receipt.setAccessKey(getElement(document, SPAN_ACCESS_KEY).text().replaceAll(" ", ""));
    }
}
