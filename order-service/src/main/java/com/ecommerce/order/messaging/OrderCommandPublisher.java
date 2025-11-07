package com.ecommerce.order.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

@Service
public class OrderCommandPublisher {

    private static final Logger log = LoggerFactory.getLogger(OrderCommandPublisher.class);

    private final BlockingQueue<Message<?>> queue = new LinkedBlockingQueue<>();

    public void sendCommand(Object command) {
        log.info("Publishing command to message broker: {}", command.getClass().getSimpleName());
        queue.add(MessageBuilder.withPayload(command).build());
        log.debug("Command queued for dispatch: {}", command);
    }

    @Bean
    public Supplier<Message<?>> orderCommands() {
        return queue::poll;
    }
}
