package com.ecommerce.paymentservice.event;

public record PaymentFailedEvent(String orderId,
                                 String reason) {
}
