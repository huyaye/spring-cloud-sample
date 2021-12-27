package com.example.orderservice.messaging.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class KafkaSinkSchema {
    private String type;
    private List<KafkaSinkField> fields;
    private boolean optional;
    private String name;
}
