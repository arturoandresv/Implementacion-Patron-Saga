package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderRequestCreateDTO;
import com.ecommerce.order.dto.OrderResponseDTO;
import com.ecommerce.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    OrderResponseDTO toDTO(Order order);

    Order toEntity(OrderRequestCreateDTO dto);
}
