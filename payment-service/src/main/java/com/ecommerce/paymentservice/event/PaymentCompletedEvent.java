package com.ecommerce.paymentservice.event;

public record PaymentCompletedEvent(String orderId,
                                    String status) {
}
