package com.example.training.kafka;

import com.example.training.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ProductDeserializer implements Deserializer<Product> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Product deserialize(String topic, byte[] data) {
    	objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(data, Product.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing Product", e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public void close() {}
}
