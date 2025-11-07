package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderRequestCreateDTO;
import com.ecommerce.order.dto.OrderResponseCreateDTO;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderStatus;
import java.sql.Timestamp;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T17:49:50-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponseCreateDTO toDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        UUID orderId = null;
        String productId = null;
        Integer quantity = null;
        OrderStatus status = null;
        Timestamp createdAt = null;

        OrderResponseCreateDTO orderResponseCreateDTO = new OrderResponseCreateDTO( orderId, productId, quantity, status, createdAt );

        return orderResponseCreateDTO;
    }

    @Override
    public Order toEntity(OrderRequestCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Order order = new Order();

        return order;
    }
}
