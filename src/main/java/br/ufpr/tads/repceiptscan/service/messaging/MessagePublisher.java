package br.ufpr.tads.repceiptscan.service.messaging;


import br.ufpr.tads.repceiptscan.dto.response.ReceiptResponseDTO;

public interface MessagePublisher {

    void publish(ReceiptResponseDTO responseDTO);

}
