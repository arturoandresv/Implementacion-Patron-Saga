package com.ecommerce.inventoryservice.command;

public record ReserveInventoryCommand(
        String orderId,
        String productId,
        Integer quantity
) {
}
