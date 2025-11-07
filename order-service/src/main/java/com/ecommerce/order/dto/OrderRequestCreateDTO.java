package com.ecommerce.order.dto;

public record OrderRequestCreateDTO(
        String productId,
        Integer quantity) {
}
