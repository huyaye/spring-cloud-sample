package com.example.catalogservice.messaging;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {
    private CatalogRepository catalogRepository;
    private ObjectMapper mapper = new ObjectMapper();

    public KafkaConsumer(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {
        log.debug("Received kafka message : " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>(){});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        String productId = (String) map.get("productId");
        CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);
        if (catalogEntity != null) {
            catalogEntity.setStock(catalogEntity.getStock() - (Integer) map.get("qty"));
            catalogRepository.save(catalogEntity);
        } else {
            log.error("Invalid productId : " + productId);
        }
    }
}
