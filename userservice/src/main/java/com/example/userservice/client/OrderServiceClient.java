package com.example.userservice.client;

import com.example.userservice.client.error.OrderServiceClientErrorDecoder;
import com.example.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// Eureka 서비스명
@FeignClient(name = "order-service", configuration = OrderServiceClientErrorDecoder.class)
public interface OrderServiceClient {
    @GetMapping("/order-service/{userId}/orders")
    List<ResponseOrder> getOrders(@PathVariable String userId);
}
