package com.ecommerce.inventoryservice.messaging;

import com.ecommerce.inventoryservice.command.ReserveInventoryCommand;
import com.ecommerce.inventoryservice.entity.InventoryItem;
import com.ecommerce.inventoryservice.event.InventoryRejectedEvent;
import com.ecommerce.inventoryservice.event.InventoryReservedEvent;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class InventoryConsumerPublisher {

    private static final Logger log = LoggerFactory.getLogger(InventoryConsumerPublisher.class);

    private final InventoryRepository inventoryRepository;
    private final BlockingQueue<Message<?>> eventQueue = new LinkedBlockingQueue<>();

    @Bean
    public Consumer<Message<ReserveInventoryCommand>> reserveInventory() {
        return msg -> {
            var cmd = msg.getPayload();
            log.info("Received ReserveInventoryCommand for orderId={} and productId={}", cmd.orderId(), cmd.productId());

            Optional<InventoryItem> itemOpt = inventoryRepository.findByProductId(cmd.productId());
            if (itemOpt.isEmpty()) {
                log.warn("Product with ID={} not found in inventory for order {}", cmd.productId(), cmd.orderId());
                eventQueue.add(MessageBuilder.withPayload(
                        new InventoryRejectedEvent(cmd.orderId(), cmd.productId(), "Product not found")).build());
                return;
            }

            InventoryItem item = itemOpt.get();

            if (item.getAvailableQuantity() >= cmd.quantity()) {
                log.info("Reserving {} units of product {} for order {}", cmd.quantity(), cmd.productId(), cmd.orderId());
                item.setAvailableQuantity(item.getAvailableQuantity() - cmd.quantity());
                inventoryRepository.save(item);

                BigDecimal total = item.getPrice().multiply(BigDecimal.valueOf(cmd.quantity()));
                log.info("Inventory successfully reserved for order {}. Total amount: {}", cmd.orderId(), total);

                eventQueue.add(MessageBuilder.withPayload(
                        new InventoryReservedEvent(cmd.orderId(), cmd.productId(), cmd.quantity(), total)).build());

            } else {
                log.warn("Insufficient stock for product {} (available={}, requested={}) for order {}",
                        cmd.productId(), item.getAvailableQuantity(), cmd.quantity(), cmd.orderId());
                eventQueue.add(MessageBuilder.withPayload(
                        new InventoryRejectedEvent(cmd.orderId(), cmd.productId(), "Insufficient stock")).build());
            }
        };
    }

    @Bean
    public Supplier<Message<?>> inventoryEvents() {
        return eventQueue::poll;
    }

}
