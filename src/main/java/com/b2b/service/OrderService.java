package com.b2b.service;

import com.b2b.dto.OrderDTO;
import com.b2b.dto.OrderCreateRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDTO createOrder(OrderCreateRequest request);
    OrderDTO getOrderById(UUID id);
    List<OrderDTO> getOrdersByBuyer(UUID buyerId);
    List<OrderDTO> getOrdersBySeller(UUID sellerId);
    OrderDTO updateOrderStatus(UUID id, String status);
}
