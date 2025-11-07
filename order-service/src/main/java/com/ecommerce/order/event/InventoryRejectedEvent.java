package com.ecommerce.order.event;

public record InventoryRejectedEvent(String orderId,
                                     String productId,
                                     String reason) {
}
