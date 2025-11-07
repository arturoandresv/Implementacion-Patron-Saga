package com.ecommerce.inventoryservice.event;

public record InventoryRejectedEvent(
        String orderId,
        String productId,
        String reason
) {
}
