package br.ufpr.tads.receiptscan.service;

import br.ufpr.tads.receiptscan.model.Receipt;
import br.ufpr.tads.receiptscan.service.messaging.RabbitPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReceiptPublisher {

    @Autowired
    private RabbitPublisher rabbitPublisher;

    public void publishReceipt(Receipt receipt) {
        log.info("Publishing receipt: {}", receipt.getId());
        rabbitPublisher.publish(receipt);
    }

}
