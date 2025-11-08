package com.ecommerce.order.dto;

import com.ecommerce.order.entity.OrderStatus;

import java.sql.Timestamp;
import java.util.UUID;

public record OrderResponseDTO(
        UUID orderId,
        String productId,
        Integer quantity,
        OrderStatus status,
        Timestamp createdAt
) {
}
