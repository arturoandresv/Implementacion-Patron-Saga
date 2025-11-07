package com.ecommerce.inventoryservice.command;

public record ReleaseInventoryCommand(String orderId, String productId, Integer quantity) {
}
