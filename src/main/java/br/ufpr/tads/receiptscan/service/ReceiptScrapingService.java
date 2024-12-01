package br.ufpr.tads.receiptscan.service;

import br.ufpr.tads.receiptscan.mapper.ReceiptPageMapper;
import br.ufpr.tads.receiptscan.model.Receipt;
import br.ufpr.tads.receiptscan.utils.HTMLReader;
import br.ufpr.tads.receiptscan.utils.PageConnectionFactory;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;

@Service
public class ReceiptScrapingService {

    @Autowired
    private PageConnectionFactory pageConnectionFactory;

    @Autowired
    private HTMLReader htmlReader;

    @Autowired
    private ReceiptPageMapper receiptPageMapper;

    public Receipt scrapeReceipt(String url) {
        HttpURLConnection connection = pageConnectionFactory.getConnection(url);
        Document document = htmlReader.getDocument(connection);
        return receiptPageMapper.map(document);
    }
}

