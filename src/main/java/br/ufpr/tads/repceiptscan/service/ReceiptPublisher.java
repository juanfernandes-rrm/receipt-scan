package br.ufpr.tads.repceiptscan.service;

import br.ufpr.tads.repceiptscan.model.Receipt;
import br.ufpr.tads.repceiptscan.service.messaging.RabbitPublisher;
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
