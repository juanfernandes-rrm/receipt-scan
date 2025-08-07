package br.ufpr.tads.receiptscan.service.messaging;


import br.ufpr.tads.receiptscan.model.ProcessedReceipt;

public interface MessagePublisher {

    void publish(ProcessedReceipt receipt);

}
