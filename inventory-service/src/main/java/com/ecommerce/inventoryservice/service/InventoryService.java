package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public boolean reserve(String productId, int quantity) {
        var item = repository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (item.getAvailableQuantity() >= quantity) {
            item.setAvailableQuantity(item.getAvailableQuantity() - quantity);
            repository.save(item);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void release(String productId, int quantity) {
        var item = repository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        item.setAvailableQuantity(item.getAvailableQuantity() + quantity);
        repository.save(item);
    }
}
