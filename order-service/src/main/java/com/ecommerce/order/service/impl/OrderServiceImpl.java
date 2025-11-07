package com.ecommerce.order.service.impl;

import com.ecommerce.order.command.ReserveInventoryCommand;
import com.ecommerce.order.dto.OrderRequestCreateDTO;
import com.ecommerce.order.dto.OrderResponseCreateDTO;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.messaging.OrderCommandPublisher;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderCommandPublisher commandPublisher;

    @Override
    public OrderResponseCreateDTO createOrder(OrderRequestCreateDTO orderRequestCreateDTO) {
        Order order = Order.builder()
                        .productId(orderRequestCreateDTO.productId())
                        .quantity(orderRequestCreateDTO.quantity())
                        .status(OrderStatus.CREATED)
                        //.createdAt(new Timestamp(System.currentTimeMillis()))
                        .build();
        orderRepository.save(order);

        ReserveInventoryCommand command = new ReserveInventoryCommand(
                order.getId().toString(),
                order.getProductId(),
                order.getQuantity()
        );

        commandPublisher.sendCommand(command);

        return orderMapper.toDTO(order);
    }
}
