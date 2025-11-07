package com.ecommerce.order.messaging;

import com.ecommerce.order.command.ProcessPaymentCommand;
import com.ecommerce.order.command.ReleaseInventoryCommand;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.event.InventoryRejectedEvent;
import com.ecommerce.order.event.InventoryReservedEvent;
import com.ecommerce.order.event.PaymentCompletedEvent;
import com.ecommerce.order.event.PaymentFailedEvent;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    private final OrderRepository orderRepository;
    private final OrderCommandPublisher orderCommandPublisher;

    private Optional<Order> findOrder(String orderIdStr) {
        try {
            UUID orderId = UUID.fromString(orderIdStr);
            return orderRepository.findById(orderId);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format for orderId={}", orderIdStr);
            return Optional.empty();
        }
    }

    @Bean
    public Consumer<Message<InventoryReservedEvent>> inventoryReserved() {
        return msg -> {
            var event = msg.getPayload();
            log.info("Received InventoryReservedEvent for orderId={}", event.orderId());

            Optional<Order> orderOpt = findOrder(event.orderId());
            if (orderOpt.isEmpty()) {
                log.error("[InventoryReserved] Order not found for ID {}", event.orderId());
                return;
            }

            Order order = orderOpt.get();
            order.setStatus(OrderStatus.PENDING_PAYMENT);
            orderRepository.save(order);
            log.info("Order {} updated to PENDING_PAYMENT", order.getId());

            // Send command to payment-service
            orderCommandPublisher.sendCommand(
                    new ProcessPaymentCommand(order.getId().toString(), event.totalAmount()));
            log.info("ProcessPaymentCommand sent for order {}", order.getId());
        };
    }

    @Bean
    public Consumer<Message<InventoryRejectedEvent>> inventoryRejected() {
        return msg -> {
            var event = msg.getPayload();
            log.warn("Received InventoryRejectedEvent for orderId={} - Reason: {}",
                    event.orderId(), event.reason());

            Optional<Order> orderOpt = findOrder(event.orderId());
            if (orderOpt.isEmpty()) {
                log.error("[InventoryRejected] Order not found for ID {}", event.orderId());
                return;
            }

            Order order = orderOpt.get();
            order.setStatus(OrderStatus.REJECTED);
            orderRepository.save(order);
            log.info("Order {} marked as REJECTED due to inventory rejection", order.getId());
        };
    }

    @Bean
    public Consumer<Message<PaymentCompletedEvent>> paymentCompleted() {
        return msg -> {
            var event = msg.getPayload();
            log.info("Received PaymentCompletedEvent for orderId={}", event.orderId());

            Optional<Order> orderOpt = findOrder(event.orderId());
            if (orderOpt.isEmpty()) {
                log.error("[paymentCompleted] Order not found for ID {}", event.orderId());
                return;
            }

            Order order = orderOpt.get();
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
            log.info("Order {} marked as COMPLETED", order.getId());
        };
    }

    @Bean
    public Consumer<Message<PaymentFailedEvent>> paymentFailed() {
        return msg -> {
            var event = msg.getPayload();
            log.warn("Received PaymentFailedEvent for orderId={} - Reason: {}",
                    event.orderId(), event.reason());

            Optional<Order> orderOpt = findOrder(event.orderId());
            if (orderOpt.isEmpty()) {
                log.error("[paymentFailed] Order not found for ID {}", event.orderId());
                return;
            }

            Order order = orderOpt.get();
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            log.info("Order {} marked as CANCELLED, sending ReleaseInventoryCommand", order.getId());

            // Send command to release reserved stock
            orderCommandPublisher.sendCommand(
                    new ReleaseInventoryCommand(order.getId().toString()));
            log.info("ReleaseInventoryCommand sent for order {}", order.getId());
        };
    }
}
