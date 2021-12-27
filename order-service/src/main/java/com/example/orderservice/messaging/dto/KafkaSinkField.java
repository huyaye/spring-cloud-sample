package com.example.orderservice.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaSinkField {
    private String type;
    private boolean optional;
    private String field;
}
