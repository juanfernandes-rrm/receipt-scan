package br.ufpr.tads.receiptscan.configuration.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitInitializer {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        amqpAdmin.initialize();
        log.info("RabbitMQ initialized");
    }

}
