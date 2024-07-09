package br.ufpr.tads.repceiptscan.configuration.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RabbitMqConfiguration {
    private final BrokerConfigurationProperties brokerConfig;
    private final List<Queue> definedQueues = new ArrayList<>();
    private final List<Exchange> definedExchanges = new ArrayList<>();

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true); // Ensure it starts with the application
        return rabbitAdmin;
    }

    @Bean
    public Declarables declareQueuesAndExchanges(RabbitAdmin rabbitAdmin) {
        List<Declarable> declarables = new ArrayList<>();

        if (brokerConfig != null && brokerConfig.getQueues() != null) {
            var queueList = brokerConfig.getQueues().values().stream()
                    .filter(Objects::nonNull)
                    .map(queueProperties -> new Queue(queueProperties.getName(), true))
                    .toList();

            definedQueues.addAll(queueList);
            queueList.forEach(rabbitAdmin::declareQueue);
            log.info("Declared queues: {}", queueList);
            declarables.addAll(queueList);
        }

        if (brokerConfig != null && brokerConfig.getExchanges() != null) {
            var exchangesList = brokerConfig.getExchanges().values().stream()
                    .filter(Objects::nonNull)
                    .map(exchangeProperties -> {
                        switch (exchangeProperties.getType().toLowerCase()) {
                            case "topic":
                                return new TopicExchange(exchangeProperties.getName());
                            case "fanout":
                                return new FanoutExchange(exchangeProperties.getName());
                            case "headers":
                                return new HeadersExchange(exchangeProperties.getName());
                            case "direct":
                            default:
                                return new DirectExchange(exchangeProperties.getName());
                        }
                    })
                    .toList();

            definedExchanges.addAll(exchangesList);
            exchangesList.forEach(rabbitAdmin::declareExchange);
            log.info("Declared exchanges: {}", exchangesList);
            declarables.addAll(exchangesList);
        }

        return new Declarables(declarables);
    }

    @Bean
    public Declarables declareBindings(RabbitAdmin rabbitAdmin) {
        if (brokerConfig == null || brokerConfig.getBindings() == null) {
            return new Declarables();
        }

        var bindingsList = brokerConfig.getBindings().values().stream()
                .map(bindingProperties -> {
                    log.info("Creating binding between exchange {} and queue {} with routing key {}",
                            bindingProperties.getExchange(), bindingProperties.getQueue(), bindingProperties.getRoutingKey());
                    Queue queue = findQueueByName(bindingProperties.getQueue());
                    Exchange exchange = findExchangeByName(bindingProperties.getExchange());

                    if (queue == null || exchange == null) {
                        log.warn("Queue or Exchange not found for binding: {} - {}", bindingProperties.getQueue(), bindingProperties.getExchange());
                        return null;
                    }

                    return BindingBuilder.bind(queue)
                            .to(exchange)
                            .with(bindingProperties.getRoutingKey())
                            .noargs();
                })
                .filter(Objects::nonNull)
                .toList();

        bindingsList.forEach(rabbitAdmin::declareBinding);
        log.info("Declared bindings: {}", bindingsList);
        return new Declarables(bindingsList);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    private Queue findQueueByName(String queueName) {
        return definedQueues.stream()
                .filter(queue -> queueName.equals(queue.getName()))
                .findFirst()
                .orElse(null);
    }

    private Exchange findExchangeByName(String exchangeName) {
        return definedExchanges.stream()
                .filter(exchange -> exchangeName.equals(exchange.getName()))
                .findFirst()
                .orElse(null);
    }
}

