package br.ufpr.tads.receiptscan.service.messaging;

import br.ufpr.tads.receiptscan.dto.response.ProcessedReceiptResponseDTO;
import br.ufpr.tads.receiptscan.mapper.ReceiptMapper;
import br.ufpr.tads.receiptscan.model.ProcessedReceipt;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitPublisher implements MessagePublisher {

    @Value("${broker.queues.processed-receipt.name}")
    private String processedReceiptQueueName;

    @Value("${broker.exchanges.processed-receipt.name}")
    private String processedReceiptExchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void publish(@Payload ProcessedReceipt receipt) {
        ProcessedReceiptResponseDTO payload = receiptMapper.map(receipt);
        log.info("Notifying queue: {} of text {}", processedReceiptQueueName, payload);
        rabbitTemplate.convertAndSend(processedReceiptExchangeName, processedReceiptQueueName, payload);
    }

}
