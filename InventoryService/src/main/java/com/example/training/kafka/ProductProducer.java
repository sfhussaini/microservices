package com.example.training.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.training.Product;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ProductProducer {

    private final KafkaTemplate<String, Product> kafkaTemplate;

    // Define the topic name for your Kafka message
    private static final String TOPIC = "inventory-product-events";

    @Autowired
    public ProductProducer(KafkaTemplate<String, Product> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Method to send product details to Kafka
    public void sendProductEvent(Product product) {
        System.out.println("Sending Product Event:");
    	kafkaTemplate.send(TOPIC, product.getSku(), product);  // using SKU as the key
        System.out.println("SKU: " + product.getSku());
        System.out.println("Name: " + product.getName());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Created At: " + product.getCreatedAt());
    	System.out.println("---------Event Sent------------");        
    }
}
