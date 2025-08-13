package com.b2b.service.impl;

import com.b2b.dto.OrderCreateRequest;
import com.b2b.dto.OrderDTO;
import com.b2b.dto.OrderItemDTO;
import com.b2b.entity.*;
import com.b2b.mapper.OrderItemMapper;
import com.b2b.mapper.OrderMapper;
import com.b2b.repository.*;
import com.b2b.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDTO createOrder(OrderCreateRequest request) {
        User buyer = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        User seller = userRepository.findById(request.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (var itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            BigDecimal price = product.getPrice();
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .price(price)
                    .subtotal(subtotal)
                    .build();

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(subtotal);
        }

        Order order = Order.builder()
                .buyer(buyer)
                .seller(seller)
                .status(Order.Status.PLACED)
                .paymentStatus(Order.PaymentStatus.PENDING)
                .totalAmount(totalAmount)
                .build();

        order = orderRepository.save(order);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }

        OrderDTO orderDTO = orderMapper.toDTO(order);
        orderDTO.setItems(
                orderItemRepository.findByOrder(order).stream()
                        .map(orderItemMapper::toDTO)
                        .collect(Collectors.toList())
        );
        return orderDTO;
    }

    @Override
    public OrderDTO getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        OrderDTO dto = orderMapper.toDTO(order);
        dto.setItems(orderItemRepository.findByOrder(order).stream()
                .map(orderItemMapper::toDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public List<OrderDTO> getOrdersByBuyer(UUID buyerId) {
        User buyer = userRepository.findById(buyerId).orElseThrow(() -> new RuntimeException("Buyer not found"));
        return orderRepository.findByBuyer(buyer)
                .stream().map(order -> {
                    OrderDTO dto = orderMapper.toDTO(order);
                    dto.setItems(orderItemRepository.findByOrder(order).stream()
                            .map(orderItemMapper::toDTO)
                            .collect(Collectors.toList()));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersBySeller(UUID sellerId) {
        User seller = userRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found"));
        return orderRepository.findBySeller(seller)
                .stream().map(order -> {
                    OrderDTO dto = orderMapper.toDTO(order);
                    dto.setItems(orderItemRepository.findByOrder(order).stream()
                            .map(orderItemMapper::toDTO)
                            .collect(Collectors.toList()));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrderStatus(UUID id, String statusStr) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            Order.Status status = Order.Status.valueOf(statusStr.toUpperCase());
            order.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + statusStr);
        }

        order = orderRepository.save(order);
        OrderDTO dto = orderMapper.toDTO(order);
        dto.setItems(orderItemRepository.findByOrder(order).stream()
                .map(orderItemMapper::toDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}
