package br.ufpr.tads.repceiptscan.service.messaging;


import br.ufpr.tads.repceiptscan.dto.response.ReceiptResponseDTO;
import br.ufpr.tads.repceiptscan.model.Receipt;

public interface MessagePublisher {

    void publish(Receipt receipt);

}
