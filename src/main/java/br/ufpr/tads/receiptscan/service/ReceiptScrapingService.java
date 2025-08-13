package br.ufpr.tads.receiptscan.service;

import br.ufpr.tads.receiptscan.exception.ScrapingFailedException;
import br.ufpr.tads.receiptscan.mapper.ReceiptPageMapper;
import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import br.ufpr.tads.receiptscan.utils.PageConnectionFactory;
import br.ufpr.tads.receiptscan.utils.PageValidate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReceiptScrapingService {

    @Autowired
    private PageConnectionFactory pageConnectionFactory;

    @Autowired
    private PageValidate pageValidate;

    @Autowired
    private ReceiptPageMapper receiptPageMapper;

    public ProcessedReceipt scrapeReceipt(String url) {
        try {
            String encodedUrl = url.replace("|", "%7C");
            Document document = Jsoup.connect(encodedUrl).get();
            pageValidate.validatePage(document, encodedUrl);

            return receiptPageMapper.map(document, url);
        } catch (IOException e) {
            throw new ScrapingFailedException("Failed to scrape receipt from URL: " + url + ". Error: " + e.getMessage(), e);
        }
    }
}

