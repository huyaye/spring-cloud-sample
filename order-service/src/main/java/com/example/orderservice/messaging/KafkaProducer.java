package com.example.orderservice.messaging;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.messaging.dto.KafkaOrderDto;
import com.example.orderservice.messaging.dto.KafkaOrderPayload;
import com.example.orderservice.messaging.dto.KafkaSinkField;
import com.example.orderservice.messaging.dto.KafkaSinkSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper mapper = new ObjectMapper();

    List<KafkaSinkField> orderFields = Arrays.asList(
            new KafkaSinkField("string", true, "order_id"),
            new KafkaSinkField("string", true, "user_id"),
            new KafkaSinkField("string", true, "product_id"),
            new KafkaSinkField("int32", true, "qty"),
            new KafkaSinkField("int32", true, "unit_price"),
            new KafkaSinkField("int32", true, "total_price"));
    KafkaSinkSchema orderSchema = KafkaSinkSchema.builder()
            .type("struct").fields(orderFields).optional(false).name("orders")
            .build();

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderDto informOrderInserted(OrderDto orderDto) {
        sendTopic("example-catalog-topic", orderDto);
        return orderDto;
    }

    // Kafka sink
    public void insertNewOrder(OrderDto orderDto) {
        KafkaOrderPayload payload = KafkaOrderPayload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .product_id(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();
        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(orderSchema, payload);
        sendTopic("orders", kafkaOrderDto);
    }

    private void sendTopic(String topic, Object message) {
        String json = "";
        try {
            json = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        kafkaTemplate.send(topic, json);
        log.info("Send kafka message to Topic : " + topic + "\nMessage : " + json);
    }
}
