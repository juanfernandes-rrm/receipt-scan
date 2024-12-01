package br.ufpr.tads.receiptscan.configuration.rabbitmq;

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

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Declarables queuesAndExchanges() {
        List<Declarable> declarables = new ArrayList<>();

        if (brokerConfig != null && brokerConfig.getQueues() != null) {
            var queueList = brokerConfig.getQueues().values().stream()
                    .filter(Objects::nonNull)
                    .map(queueProperties -> new Queue(queueProperties.getName(), true))
                    .toList();

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

            log.info("Declared exchanges: {}", exchangesList);
            declarables.addAll(exchangesList);
        }

        return new Declarables(declarables);
    }

    @Bean
    public Declarables bindings(RabbitAdmin rabbitAdmin) {
        if (brokerConfig == null || brokerConfig.getBindings() == null) {
            return new Declarables();
        }

        List<Binding> bindingsList = brokerConfig.getBindings().values().stream()
                .map(bindingProperties -> {
                    log.info("Creating binding between exchange {} and queue {} with routing key {}",
                            bindingProperties.getExchange(), bindingProperties.getQueue(), bindingProperties.getRoutingKey());
                    Queue queue = new Queue(bindingProperties.getQueue(), true);
                    Exchange exchange = new DirectExchange(bindingProperties.getExchange());

                    return BindingBuilder.bind(queue)
                            .to(exchange)
                            .with(bindingProperties.getRoutingKey())
                            .noargs();
                })
                .toList();

        log.info("Declared bindings: {}", bindingsList);
        bindingsList.forEach(binding -> log.info("Binding {} shouldDeclare: {}", binding.getExchange(), binding.shouldDeclare()));
        return new Declarables(bindingsList);
    }
}

