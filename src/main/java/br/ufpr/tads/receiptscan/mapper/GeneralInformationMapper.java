package br.ufpr.tads.receiptscan.mapper;

import br.ufpr.tads.receiptscan.model.Receipt;
import br.ufpr.tads.receiptscan.utils.FormatValues;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.ufpr.tads.receiptscan.utils.HtmlUtils.getElement;

@Component
public class GeneralInformationMapper {

    @Autowired
    private FormatValues formatValues;

    public static final String SECTION_INFORMATION = "div#infos";
    public static final String INFORMATION_NUMBER = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(4)";
    public static final String INFORMATION_SERIES = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(5)";
    public static final String INFORMATION_ISSUANCE = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(6)";
    public static final String INFORMATION_PROTOCOL = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(9)";

    public void mapGeneralInformation(Receipt receipt, Document document) {
        receipt.setNumber(getNextTextNodeFrom(document, INFORMATION_NUMBER));
        receipt.setSeries(getNextTextNodeFrom(document, INFORMATION_SERIES));
        mapIssuanceInformation(receipt, getNextTextNodeFrom(document, INFORMATION_ISSUANCE));
        mapAuthorizationInformation(receipt, getNextTextNodeFrom(document, INFORMATION_PROTOCOL));
    }

    private void mapIssuanceInformation(Receipt receipt, String issuanceString) {
        String[] split = issuanceString.split(" ");
        receipt.setIssuanceDate(formatValues.formatDateTime(split[0] + split[1]));
        receipt.setIssuer(split[4]);
    }

    private void mapAuthorizationInformation(Receipt receipt, String protocol) {
        String[] split = protocol.split(" ");
        receipt.setAuthorizationCode(split[0]);
        receipt.setAuthorizationDate(formatValues.formatDateTime(split[1] + split[2]));
    }

    private String getNextTextNodeFrom(Document document, String select) {
        Element element = getElement(document, select);
        return Optional.ofNullable(element.nextSibling())
                .map(text -> text.toString().trim())
                .orElseThrow(() -> new ElementNotFoundException("Text node not found"));
    }

}

