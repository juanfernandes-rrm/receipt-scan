package br.ufpr.tads.repceiptscan.service;

import br.ufpr.tads.repceiptscan.dto.request.ReceiptRequestDTO;
import br.ufpr.tads.repceiptscan.dto.response.ReceiptResponseDTO;
import br.ufpr.tads.repceiptscan.mapper.ReceiptMapper;
import br.ufpr.tads.repceiptscan.mapper.ReceiptPageMapper;
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
    private ReceiptPageMapper receiptPageMapper;

    @Autowired
    private ReceiptMapper receiptMapper;

    public ReceiptResponseDTO scan(ReceiptRequestDTO receiptRequestDTO) {
        receiptURLValidate.validate(receiptRequestDTO.getUrl());
        HttpURLConnection connection = pageConnectionFactory.getConnection(receiptRequestDTO.getUrl());
        Document document = getDocument(connection);
        pageValidate.validatePage(document);
        return receiptMapper.map(receiptPageMapper.map(document));
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
