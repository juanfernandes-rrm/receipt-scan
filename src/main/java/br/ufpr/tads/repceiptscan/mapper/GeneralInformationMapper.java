package br.ufpr.tads.repceiptscan.mapper;

import br.ufpr.tads.repceiptscan.model.AuthorizationProtocol;
import br.ufpr.tads.repceiptscan.model.GeneralInformation;
import br.ufpr.tads.repceiptscan.model.Issuance;
import br.ufpr.tads.repceiptscan.utils.FormatValues;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.ufpr.tads.repceiptscan.utils.HtmlUtils.getElement;

@Component
public class GeneralInformationMapper {

    @Autowired
    private FormatValues formatValues;

    public static final String SECTION_INFORMATION = "div#infos";
    public static final String INFORMATION_NUMBER = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(4)";
    public static final String INFORMATION_SERIES = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(5)";
    public static final String INFORMATION_ISSUANCE = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(6)";
    public static final String INFORMATION_PROTOCOL = SECTION_INFORMATION + " > div:nth-child(1) > ul > li > strong:nth-child(9)";

    public GeneralInformation mapGeneralInformation(Document document) {
        GeneralInformation generalInformation = new GeneralInformation();
        generalInformation.setNumber(getNextTextNodeFrom(document, INFORMATION_NUMBER));
        generalInformation.setSeries(getNextTextNodeFrom(document, INFORMATION_SERIES));
        generalInformation.setIssuance(extractIssuance(getNextTextNodeFrom(document, INFORMATION_ISSUANCE)));
        generalInformation.setAuthorizationProtocol(extractAuthorizationProtocol(getNextTextNodeFrom(document, INFORMATION_PROTOCOL)));
        return generalInformation;
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

    private String getNextTextNodeFrom(Document document, String select) {
        Element element = getElement(document, select);
        return Optional.ofNullable(element.nextSibling())
                .map(text -> text.toString().trim())
                .orElseThrow(() -> new ElementNotFoundException("Text node not found"));
    }

}

