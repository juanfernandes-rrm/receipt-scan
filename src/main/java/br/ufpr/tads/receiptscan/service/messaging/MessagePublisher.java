package br.ufpr.tads.receiptscan.service.messaging;


import br.ufpr.tads.receiptscan.model.Receipt;

public interface MessagePublisher {

    void publish(Receipt receipt);

}
