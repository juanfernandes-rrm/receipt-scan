package br.ufpr.tads.receiptscan.service;

import br.ufpr.tads.receiptscan.mapper.ReceiptPageMapper;
import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import br.ufpr.tads.receiptscan.utils.HTMLReader;
import br.ufpr.tads.receiptscan.utils.PageConnectionFactory;
import br.ufpr.tads.receiptscan.utils.PageValidate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReceiptScrapingServiceIntegrationTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private PageConnectionFactory pageConnectionFactory;

    @Autowired
    private HTMLReader htmlReader;

    @Autowired
    private PageValidate pageValidate;

    @Autowired
    private ReceiptPageMapper receiptPageMapper;

    @Autowired
    private ReceiptScrapingService receiptScrapingService;

    private Document testDocument;

    @BeforeEach
    void setUp() throws IOException {
        ClassPathResource resource = new ClassPathResource("html/receipt.html");
        try (InputStream inputStream = resource.getInputStream()) {
            String htmlContent = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            this.testDocument = Jsoup.parse(htmlContent);
        }
    }

    @Test
    void shouldParseHtmlAndExtractDataCorrectly() {
        ProcessedReceipt receipt = receiptPageMapper.map(testDocument, "local-test-url");

        assertThat(receipt).isNotNull();
        assertThat(receipt.getStoreName()).isEqualTo("MERCADO SALLA LTDA");
        assertThat(receipt.getStoreCnpj()).isEqualTo("11522691000100");
        assertThat(receipt.getStoreAddress()).isEqualTo("AVENIDA JURITI, 321, , JD CLAUDIA, PINHAIS, PR");
        assertThat(receipt.getTotalValue()).isEqualTo(new BigDecimal("56.33"));
        assertThat(receipt.getTotalItems()).isEqualTo(11);
        assertThat(receipt.getAccessKey()).isEqualTo("41220911522691000100651180004758151003461118");
        assertThat(receipt.getAuthorizationCode()).isEqualTo("141221277511180");
        assertThat(receipt.getDiscount()).isEqualTo("0");
        assertThat(receipt.getAuthorizationDate().format(formatter)).isEqualTo("2022-09-19 17:32:16");
        assertThat(receipt.getValueToPay()).isEqualTo(new BigDecimal("56.33"));
        assertThat(receipt.getPaymentMethod().name()).isEqualTo("CREDIT_CARD");
        assertThat(receipt.getValuePaid()).isEqualTo(new BigDecimal("56.33"));
        assertThat(receipt.getTax()).isEqualTo(new BigDecimal("5.70"));
        assertThat(receipt.getNumber()).isEqualTo("475815");
        assertThat(receipt.getSeries()).isEqualTo("118");
        assertThat(receipt.getIssuer()).isEqualTo("Via Consumidor");
        assertThat(receipt.getIssuanceDate().format(formatter)).isEqualTo("2022-09-19 17:32:00");
        assertThat(receipt.getConsumerCpf()).isNull();
        assertThat(receipt.getConsumerName()).isNull();

        var firstItem = receipt.getItems().get(0);
        assertThat(firstItem.getProductDescription()).isEqualTo("MEXERICA KG");
        assertThat(firstItem.getProductCode()).isEqualTo("25");
        assertThat(firstItem.getQuantity()).isEqualTo("0.985");
        assertThat(firstItem.getUnit()).isEqualTo("KG");
        assertThat(firstItem.getUnitValue()).isEqualTo(new BigDecimal("1.99"));
        assertThat(firstItem.getTotalValue()).isEqualTo(new BigDecimal("1.96"));
    }
}
