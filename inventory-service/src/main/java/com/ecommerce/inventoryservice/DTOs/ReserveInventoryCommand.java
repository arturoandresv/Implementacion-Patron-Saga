package com.ecommerce.inventoryservice.DTOs;

public record ReserveInventoryCommand(String orderId, String productId, int quantity) {}

