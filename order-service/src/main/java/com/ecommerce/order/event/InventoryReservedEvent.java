package com.ecommerce.order.event;

import java.math.BigDecimal;

public record InventoryReservedEvent(String orderId,
                                     String productId,
                                     Integer quantity,
                                     BigDecimal totalAmount) {
}
