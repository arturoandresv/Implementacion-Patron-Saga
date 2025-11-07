package com.ecommerce.order.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

@Service
public class OrderCommandPublisher {
    private final BlockingQueue<Message<Object>> queue = new LinkedBlockingQueue<>();

    public void sendCommand(Object command) {
        queue.add(MessageBuilder.withPayload(command).build());
    }

    @Bean
    public Supplier<Message<Object>> orderCommands() {
        return queue::poll;
    }
}
