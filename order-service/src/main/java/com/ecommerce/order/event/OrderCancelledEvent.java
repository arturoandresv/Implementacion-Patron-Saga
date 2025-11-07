package com.ecommerce.order.event;

import java.util.UUID;

public record OrderCancelledEvent(UUID orderId,
                                  String reason) {
}
