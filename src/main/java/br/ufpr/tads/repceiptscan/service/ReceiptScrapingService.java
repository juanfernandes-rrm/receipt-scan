package br.ufpr.tads.repceiptscan.service;

import br.ufpr.tads.repceiptscan.mapper.ReceiptPageMapper;
import br.ufpr.tads.repceiptscan.model.Receipt;
import br.ufpr.tads.repceiptscan.utils.HTMLReader;
import br.ufpr.tads.repceiptscan.utils.PageConnectionFactory;
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

