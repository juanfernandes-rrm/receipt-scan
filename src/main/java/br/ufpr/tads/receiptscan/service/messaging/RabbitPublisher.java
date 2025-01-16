package br.ufpr.tads.receiptscan.service.messaging;

import br.ufpr.tads.receiptscan.dto.ProductsDTO;
import br.ufpr.tads.receiptscan.dto.response.AddressDTO;
import br.ufpr.tads.receiptscan.dto.response.ItemDTO;
import br.ufpr.tads.receiptscan.dto.response.StoreDTO;
import br.ufpr.tads.receiptscan.model.Receipt;
import br.ufpr.tads.receiptscan.model.Store;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RabbitPublisher implements MessagePublisher {

    @Value("${broker.queues.catalog.name}")
    private String catalogQueueName;
    @Value("${broker.exchanges.catalog.name}")
    private String catalogExchangeName;

    @Value("${broker.queues.user.name}")
    private String userQueueName;
    @Value("${broker.exchanges.user.name}")
    private String userExchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void publish(@Payload Receipt receipt) {
        try {
            sendMessage(catalogExchangeName, catalogQueueName, getMessageToCatalog(receipt));
            sendMessage(userExchangeName, userQueueName, getMessageToUser(receipt));
        } catch (JsonProcessingException e) {
            log.error("Error while processing message to send to the queue", e);
        }
    }

    //TODO: criar abstração para cada fila
    private StoreDTO getMessageToUser(Receipt receipt) throws JsonProcessingException {
        Store store = receipt.getStore();
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(store.getId());
        storeDTO.setCNPJ(store.getCNPJ());
        storeDTO.setName(store.getName());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(store.getAddress().getCity().getName());
        addressDTO.setState(store.getAddress().getCity().getState().name());
        addressDTO.setNeighborhood(store.getAddress().getNeighborhood());
        addressDTO.setStreet(store.getAddress().getStreet());
        addressDTO.setNumber(store.getAddress().getNumber());
        storeDTO.setAddress(addressDTO);
        return storeDTO;
    }

    //TODO: criar abstração para cada fila
    private ProductsDTO getMessageToCatalog(Receipt receipt) throws JsonProcessingException {
        List<ItemDTO> itemsDTO = new ArrayList<>();
        receipt.getItemDetails().forEach((itemDetails) -> {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setName(itemDetails.getItem().getName());
            itemDTO.setCode(itemDetails.getItem().getCode());
            itemDTO.setAmount(itemDetails.getAmount());
            itemDTO.setUnit(itemDetails.getUnit());
            itemDTO.setUnitValue(itemDetails.getUnitValue());
            itemDTO.setTotalValue(itemDetails.getTotalValue());
            itemsDTO.add(itemDTO);
        });
        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setItems(itemsDTO);
        productsDTO.setBranchId(receipt.getStore().getId());

        return productsDTO;
    }

    //TODO: criar abstração para cada fila
    private void sendMessage(String exchangeName, String queueName, ProductsDTO payload){
        log.info("Notifying queue: {} of text {}", queueName, payload);
        rabbitTemplate.convertAndSend(exchangeName, queueName, payload);
    }

    //TODO: criar abstração para cada fila
    private void sendMessage(String exchangeName, String queueName, StoreDTO payload){
        log.info("Notifying queue: {} of text {}", queueName, payload);
        rabbitTemplate.convertAndSend(exchangeName, queueName, payload);
    }

}
