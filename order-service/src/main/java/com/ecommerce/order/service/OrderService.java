package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderRequestCreateDTO;
import com.ecommerce.order.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
OrderResponseDTO createOrder(OrderRequestCreateDTO orderRequestCreateDTO);
List<OrderResponseDTO> getAllOrders();
}
