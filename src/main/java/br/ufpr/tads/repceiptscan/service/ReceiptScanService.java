package br.ufpr.tads.repceiptscan.service;

import br.ufpr.tads.repceiptscan.dto.ReceiptRequestDTO;
import br.ufpr.tads.repceiptscan.mapper.ReceiptMapper;
import br.ufpr.tads.repceiptscan.model.Receipt;
import br.ufpr.tads.repceiptscan.utils.HTMLReader;
import br.ufpr.tads.repceiptscan.utils.PageConnectionFactory;
import br.ufpr.tads.repceiptscan.utils.PageValidate;
import br.ufpr.tads.repceiptscan.utils.ReceiptURLValidate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;

@Service
public class ReceiptScanService {

    @Autowired
    private ReceiptURLValidate receiptURLValidate;

    @Autowired
    private PageConnectionFactory pageConnectionFactory;

    @Autowired
    private PageValidate pageValidate;

    @Autowired
    private HTMLReader htmlReader;

    @Autowired
    private ReceiptMapper receiptMapper;

    public Receipt scan(ReceiptRequestDTO receiptRequestDTO) {
        receiptURLValidate.validate(receiptRequestDTO.getUrl());
        HttpURLConnection connection = pageConnectionFactory.getConnection(receiptRequestDTO.getUrl());
        Document document = getDocument(connection);
        pageValidate.validatePage(document);
        return receiptMapper.map(document);
    }

    private Document getDocument(HttpURLConnection connection) {
        String html;
        try {
            html = htmlReader.getHTML(connection.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Jsoup.parse(html);
    }

}
