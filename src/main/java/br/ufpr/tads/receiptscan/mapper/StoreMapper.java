package br.ufpr.tads.receiptscan.mapper;

import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {

    private static final String STORE_NAME = "div#conteudo > div:nth-child(2) > div:nth-child(1)";
    private static final String STORE_CNPJ = "div#conteudo > div:nth-child(2) > div:nth-child(2)";
    private static final String STORE_ADDRESS = "div#conteudo > div:nth-child(2) > div:nth-child(3)";
    public static final String REGEX_TO_REMOVE_CNPJ_MASK = "[^0-9]";

    public void mapStore(ProcessedReceipt receipt, Document document) {
        receipt.setStoreName(document.select(STORE_NAME).text());
        receipt.setStoreCnpj(document.select(STORE_CNPJ).text().replace("CNPJ: ", "").replaceAll(REGEX_TO_REMOVE_CNPJ_MASK, ""));
        receipt.setStoreAddress(document.select(STORE_ADDRESS).text());
    }

}

