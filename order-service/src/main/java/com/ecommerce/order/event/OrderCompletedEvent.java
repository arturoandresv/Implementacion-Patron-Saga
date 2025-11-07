package com.ecommerce.order.event;

import java.util.UUID;

public record OrderCompletedEvent(UUID orderId,
                                  String status) {
}
