package br.ufpr.tads.repceiptscan;

import br.ufpr.tads.repceiptscan.mapper.ReceiptMapper;
import br.ufpr.tads.repceiptscan.model.Receipt;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootApplication
public class ReceiptScanApplication {

	private static final String URL = "URL DA NF-E";

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ReceiptScanApplication.class, args);

		URL obj = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("Response code: " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String html = response.toString();

		Document doc = Jsoup.parse(html);
		ReceiptMapper receiptMapper = new ReceiptMapper();
		Receipt receipt = receiptMapper.map(doc);
		System.out.println(receipt.toString());
	}

}
