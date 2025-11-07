package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderRequestCreateDTO;
import com.ecommerce.order.dto.OrderResponseCreateDTO;
import com.ecommerce.order.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseCreateDTO toDTO(Order order);
    Order toEntity(OrderRequestCreateDTO dto);
}
