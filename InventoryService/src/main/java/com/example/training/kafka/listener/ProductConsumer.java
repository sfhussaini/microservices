package com.example.training.kafka.listener;

import com.example.training.Product;
import com.example.training.kafka.ProductDeserializer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ProductConsumer {

    public static void main(String[] args) {
        String topic = "inventory-product-events";

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "product-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProductDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, Product> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        System.out.println("Listening to topic: " + topic);
        while (true) {
            ConsumerRecords<String, Product> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, Product> record : records) {
                Product product = record.value();
                System.out.println("Received Product Event:");
                System.out.println("SKU: " + product.getSku());
                System.out.println("Name: " + product.getName());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Created At: " + product.getCreatedAt());
                System.out.println("-----------------------------------");
            }
        }
    }
}
