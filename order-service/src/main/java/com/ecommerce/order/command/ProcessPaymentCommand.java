package com.ecommerce.order.command;

import java.math.BigDecimal;

public record ProcessPaymentCommand(String orderId,
                                    BigDecimal amount) {
}
