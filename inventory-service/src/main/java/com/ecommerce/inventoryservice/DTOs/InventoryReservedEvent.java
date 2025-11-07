package com.ecommerce.inventoryservice.DTOs;

import java.math.BigDecimal;

public record InventoryReservedEvent(String orderId, String productId, int quantity, BigDecimal totalAmount) {}


