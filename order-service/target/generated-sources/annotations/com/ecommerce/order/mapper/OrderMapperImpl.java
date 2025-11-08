package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderRequestCreateDTO;
import com.ecommerce.order.dto.OrderResponseDTO;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderStatus;
import java.sql.Timestamp;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-07T18:52:35-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponseDTO toDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        String productId = null;
        Integer quantity = null;
        OrderStatus status = null;
        Timestamp createdAt = null;

        productId = order.getProductId();
        quantity = order.getQuantity();
        status = order.getStatus();
        createdAt = order.getCreatedAt();

        UUID orderId = null;

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO( orderId, productId, quantity, status, createdAt );

        return orderResponseDTO;
    }

    @Override
    public Order toEntity(OrderRequestCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.productId( dto.productId() );
        order.quantity( dto.quantity() );

        return order.build();
    }
}
