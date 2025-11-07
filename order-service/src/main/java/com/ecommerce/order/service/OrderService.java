package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderRequestCreateDTO;
import com.ecommerce.order.dto.OrderResponseCreateDTO;

public interface OrderService {
OrderResponseCreateDTO createOrder(OrderRequestCreateDTO orderRequestCreateDTO);
}
