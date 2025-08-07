package br.ufpr.tads.receiptscan.mapper;

import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import br.ufpr.tads.receiptscan.utils.FormatValues;
import br.ufpr.tads.receiptscan.utils.HtmlUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class GeneralInformationMapper {

    private static final String SECTION_INFORMATION = "div#infos";
    private static final String INFORMATION_NUMBER = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(4)";
    private static final String INFORMATION_SERIES = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(5)";
    private static final String INFORMATION_ISSUANCE = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(6)";
    private static final String INFORMATION_PROTOCOL = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(9)";
    private static final String CONSUMER_CPF = SECTION_INFORMATION + "> div:nth-child(3) > ul > li > strong:contains(CPF:)";
    private static final String CONSUMER_NAME = SECTION_INFORMATION + "> div:nth-child(3) > ul > li > strong:contains(Nome:)";

    public void mapGeneralInformation(ProcessedReceipt receipt, Document document) {
        receipt.setNumber(HtmlUtils.getNextTextNodeFrom(document, INFORMATION_NUMBER));
        receipt.setSeries(HtmlUtils.getNextTextNodeFrom(document, INFORMATION_SERIES));
        mapIssuanceInformation(receipt, HtmlUtils.getNextTextNodeFrom(document, INFORMATION_ISSUANCE));
        mapAuthorizationInformation(receipt, HtmlUtils.getNextTextNodeFrom(document, INFORMATION_PROTOCOL));
        receipt.setConsumerCpf(HtmlUtils.getNullableNextTextNodeFrom(document, CONSUMER_CPF));
        receipt.setConsumerName(HtmlUtils.getNullableNextTextNodeFrom(document, CONSUMER_NAME));
    }

    private void mapIssuanceInformation(ProcessedReceipt receipt, String issuanceString) {
        String[] split = issuanceString.split("-");
        receipt.setIssuanceDate(FormatValues.formatDateTime(split[0].replace(" ", "")));
        receipt.setIssuer(split[1].trim());
    }

    private void mapAuthorizationInformation(ProcessedReceipt receipt, String protocol) {
        String[] split = protocol.split(" ");
        receipt.setAuthorizationCode(split[0]);
        receipt.setAuthorizationDate(FormatValues.formatDateTime(split[1] + split[2]));
    }

}

