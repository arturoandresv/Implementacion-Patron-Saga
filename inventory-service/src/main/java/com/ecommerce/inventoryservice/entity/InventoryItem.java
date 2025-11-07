package com.ecommerce.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "inventory_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable=false)
    private String productId;

    @Column(nullable=false)
    private int availableQuantity;

    @Column(nullable=false)
    private BigDecimal price;

}

