package com.ecommerce.paymentservice.messaging;

import com.ecommerce.paymentservice.command.ProcessPaymentCommand;
import com.ecommerce.paymentservice.event.PaymentCompletedEvent;
import com.ecommerce.paymentservice.event.PaymentFailedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class PaymentProcessor {

    private static final Logger log = LoggerFactory.getLogger(PaymentProcessor.class);

    private final BlockingQueue<Message<?>> eventQueue = new LinkedBlockingQueue<>();

    @Bean
    public Consumer<Message<ProcessPaymentCommand>> processPayment() {
        return msg -> {
            var command = msg.getPayload();
            log.info("Processing payment for order: {}", command.orderId());

            // Simulamos un fallo si el monto es muy alto (por ejemplo, > 100)
            BigDecimal limit = new BigDecimal("100.00");
            if (command.amount().compareTo(limit) <= 0) {
                eventQueue.add(MessageBuilder.withPayload(
                        new PaymentCompletedEvent(command.orderId(), "SUCCESS")).build());
                log.info("Payment successfully completed for order {}", command.orderId());
            } else {
                eventQueue.add(MessageBuilder.withPayload(
                        new PaymentFailedEvent(command.orderId(), "Monto demasiado alto")).build());
                log.warn("Payment failed for order {} - amount {} exceeds limit", command.orderId(), command.amount());
            }
        };
    }

    @Bean
    public Supplier<Message<?>> paymentEvents() {
        return eventQueue::poll;
    }
}
