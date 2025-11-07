package com.ecommerce.paymentservice.command;

import java.math.BigDecimal;

public record ProcessPaymentCommand(String orderId, BigDecimal amount) {
}
