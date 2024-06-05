package br.ufpr.tads.repceiptscan.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfiguration {

    private final static String NAME_EXCHANGE = "amq.direct";
    private final static String NAME_QUEUE = "scan";

    @Autowired
    private AmqpAdmin amqpAdmin;

//    private Queue createQueue(String name) {
//        return new Queue(name, true, false, false);
//    }
//
//    private DirectExchange createExchange() {
//        return new DirectExchange(NAME_EXCHANGE);
//    }
//
//    private Binding createBinding(Queue queue, DirectExchange exchange) {
//        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
//    }
//
//    @PostConstruct
//    private void createQueue() {
//        Queue scanQueue = createQueue(NAME_QUEUE);
//        DirectExchange directExchange = createExchange();
//        Binding binding = createBinding(scanQueue, directExchange);
//
//        amqpAdmin.declareQueue(scanQueue);
//        amqpAdmin.declareExchange(directExchange);
//        amqpAdmin.declareBinding(binding);
//    }

}
