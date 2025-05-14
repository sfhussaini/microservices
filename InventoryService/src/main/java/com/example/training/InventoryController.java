package com.example.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Add or update a product
    @PostMapping("/products")
    public Product addOrUpdateProduct(@RequestBody Product product) {
        return inventoryService.addOrUpdateProduct(product);
    }

    // Add or update a warehouse inventory
    @PostMapping("/warehouses")
    public Warehouse addOrUpdateWarehouse(@RequestBody Warehouse warehouse) {
        return inventoryService.addOrUpdateWarehouse(warehouse);
    }

    // Get a product by ID
    @GetMapping("/products/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return inventoryService.getProductById(id);
    }

    // Get all warehouses for a product
    @GetMapping("/warehouses/{productId}")
    public List<Warehouse> getWarehousesForProduct(@PathVariable Long productId) {
        return inventoryService.getWarehousesForProduct(productId);
    }

    // Update inventory quantity
    @PutMapping("/warehouses/{id}")
    public Warehouse updateInventory(@PathVariable Long id, @RequestParam int quantity) {
        return inventoryService.updateInventory(id, quantity);
    }
}
