package com.example.training;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    
    List<Warehouse> findAllByProduct_ProductId(Long productId);
}
