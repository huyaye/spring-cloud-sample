package com.example.orderservice.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KafkaOrderDto implements Serializable {
    private KafkaSinkSchema schema;
    private KafkaOrderPayload payload;
}
