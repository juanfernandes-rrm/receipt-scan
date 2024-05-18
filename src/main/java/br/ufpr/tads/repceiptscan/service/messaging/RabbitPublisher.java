package br.ufpr.tads.repceiptscan.service.messaging;

import br.ufpr.tads.repceiptscan.dto.response.ReceiptResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    @Value("${broker.queues.receipt.name}")
    private String receiptQueueName;
    @Value("${broker.exchanges.receipt.name}")
    private String receiptExchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void publish(@Payload ReceiptResponseDTO responseDTO) {
        try {
            var json = objectMapper.writeValueAsString(responseDTO);
            log.info("Notifying queue: {} of text {}", receiptQueueName, responseDTO);
            rabbitTemplate.convertAndSend(receiptExchangeName, receiptQueueName, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
