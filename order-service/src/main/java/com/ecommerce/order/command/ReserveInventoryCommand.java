package com.ecommerce.order.command;

public record ReserveInventoryCommand(
        String orderId,
        String productId,
        Integer quantity
) {
}
