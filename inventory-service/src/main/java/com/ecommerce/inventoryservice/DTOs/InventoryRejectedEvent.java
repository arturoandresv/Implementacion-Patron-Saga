package com.ecommerce.inventoryservice.DTOs;

public record InventoryRejectedEvent(String orderId, String productId, int quantity, String reason) {}

