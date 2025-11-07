package com.ecommerce.inventoryservice.DTOs;

public record ReleaseInventoryCommand(String orderId, String productId, int quantity) {}
