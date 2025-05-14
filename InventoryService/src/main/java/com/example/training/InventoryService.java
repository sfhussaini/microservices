package com.example.training;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.training.kafka.ProductProducer;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * Service class that handles operations related to inventory,
 * including products and warehouses. Integrates with Kafka for event sending
 * and Micrometer for exception monitoring.
 */
@Service
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductProducer productProducer;

    @Autowired
    private MeterRegistry meterRegistry; // Used to register and record metrics like counters and timers

    /**
     * Adds a new product or updates an existing one.
     * Sets creation and update timestamps.
     * Sends product event via Kafka and returns the saved product.
     */
    @Transactional
    public Product addOrUpdateProduct(Product product) {
        try {
            product.setCreatedAt(Optional.ofNullable(product.getCreatedAt()).orElse(LocalDateTime.now()));
            product.setUpdatedAt(LocalDateTime.now());

            Product saved = productRepository.save(product);
            productProducer.sendProductEvent(saved);

            return saved;
        } catch (Exception ex) {
            // Records an exception occurrence in Micrometer as a tagged counter
            // The metric name is "exceptions.count", with a tag "exception" set to the simple class name
            meterRegistry.counter("exceptions.count", "exception", ex.getClass().getSimpleName()).increment();
            throw ex;
        }
    }

    /**
     * Adds or updates a warehouse entry.
     * Sets the last updated timestamp.
     * Sends a Kafka event for the associated product.
     */
    @Transactional
    public Warehouse addOrUpdateWarehouse(Warehouse warehouse) {
        try {
            warehouse.setLastUpdated(LocalDateTime.now());

            Warehouse saved = warehouseRepository.save(warehouse);
            productProducer.sendProductEvent(saved.getProduct());

            return saved;
        } catch (Exception ex) {
            // Increments the counter for the specific exception type
            meterRegistry.counter("exceptions.count", "exception", ex.getClass().getSimpleName()).increment();
            throw ex;
        }
    }

    /**
     * Retrieves a product by its ID.
     */
    public Optional<Product> getProductById(Long id) {
        try {
            return productRepository.findById(id);
        } catch (Exception ex) {
            // Exception tracking with Micrometer
            meterRegistry.counter("exceptions.count", "exception", ex.getClass().getSimpleName()).increment();
            throw ex;
        }
    }

    /**
     * Retrieves all warehouses associated with a given product ID.
     */
    public List<Warehouse> getWarehousesForProduct(Long productId) {
        try {
            return warehouseRepository.findAllByProduct_ProductId(productId);
        } catch (Exception ex) {
            // Count incremented for this exception type
            meterRegistry.counter("exceptions.count", "exception", ex.getClass().getSimpleName()).increment();
            throw ex;
        }
    }

    /**
     * Updates the inventory quantity of a specific warehouse.
     * If the warehouse exists, it updates the quantity and last updated time,
     * saves the change, and sends a Kafka event for the product.
     */
    @Transactional
    public Warehouse updateInventory(Long warehouseId, int quantity) {
        try {
            Optional<Warehouse> opt = warehouseRepository.findById(warehouseId);

            if (opt.isPresent()) {
                Warehouse warehouse = opt.get();
                warehouse.setQuantity(warehouse.getQuantity() + quantity);
                warehouse.setLastUpdated(LocalDateTime.now());

                Warehouse updated = warehouseRepository.save(warehouse);
                productProducer.sendProductEvent(updated.getProduct());

                return updated;
            }

            return null;
        } catch (Exception ex) {
            // Each time an exception occurs, this counter is incremented with a tag indicating the exception type
            meterRegistry.counter("exceptions.count", "exception", ex.getClass().getSimpleName()).increment();
            throw ex;
        }
    }
}
