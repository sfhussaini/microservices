package com.example.training.kafka;

import com.example.training.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ProductSerializer implements Serializer<Product> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Product product) {
    	objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsBytes(product);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing Product", e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public void close() {}
}
