package br.ufpr.tads.receiptscan.controller;

import br.ufpr.tads.receiptscan.IntegrationTestBase;
import br.ufpr.tads.receiptscan.dto.request.ReceiptRequestDTO;
import br.ufpr.tads.receiptscan.dto.response.ProcessedReceiptResponseDTO;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class ReceiptScanControllerIntegrationTest extends IntegrationTestBase {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @LocalServerPort
    private int port;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void overrideWebClientBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("receipt-scan.nfce-url-base", wireMockServer::baseUrl);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldReturn200AndSuccessfullyScanReceipt() {
        String receiptPath = "/nfce/qrcode?p=CHAVE_DA_NOTA_TESTE";
        String fullReceiptUrl = wireMockServer.baseUrl() + receiptPath;

        wireMockServer.stubFor(get(urlEqualTo(receiptPath))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", "text/html; charset=utf-8")
                .withBodyFile("receipt.html")));

        var requestBody = new ReceiptRequestDTO(fullReceiptUrl);

        ProcessedReceiptResponseDTO receipt = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/scan")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ProcessedReceiptResponseDTO.class);

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
        assertThat(receipt.getPaymentMethod()).isEqualTo("CREDIT_CARD");
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