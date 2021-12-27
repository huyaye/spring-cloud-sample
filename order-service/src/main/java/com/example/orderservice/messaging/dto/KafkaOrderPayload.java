package com.example.orderservice.messaging.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaOrderPayload {
    private String order_id;
    private String user_id;
    private String product_id;
    private Integer qty;
    private Integer unit_price;
    private Integer total_price;
}
