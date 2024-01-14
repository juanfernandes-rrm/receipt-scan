package br.ufpr.tads.repceiptscan.service;

import br.ufpr.tads.repceiptscan.dto.response.ReceiptResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(String queueName, ReceiptResponseDTO responseDTO) {
        try {
            var json = objectMapper.writeValueAsString(responseDTO);
            rabbitTemplate.convertAndSend(queueName, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
