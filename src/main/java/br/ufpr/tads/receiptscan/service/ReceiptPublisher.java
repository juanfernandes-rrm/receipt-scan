package br.ufpr.tads.receiptscan.service;

import br.ufpr.tads.receiptscan.model.Receipt;
import br.ufpr.tads.receiptscan.service.messaging.RabbitPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptPublisher {

    @Autowired
    private RabbitPublisher rabbitPublisher;

    public void publishReceipt(Receipt receipt) {
        rabbitPublisher.publish(receipt);
    }

}
