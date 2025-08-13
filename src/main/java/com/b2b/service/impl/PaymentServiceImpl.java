package com.b2b.service.impl;

import com.b2b.dto.PaymentDTO;
import com.b2b.entity.Order;
import com.b2b.entity.Payment;
import com.b2b.entity.Payment.Status;
import com.b2b.mapper.PaymentMapper;
import com.b2b.repository.OrderRepository;
import com.b2b.repository.PaymentRepository;
import com.b2b.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDTO initiatePayment(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        Payment payment = Payment.builder()
                .order(order)
                .amount(order.getTotalAmount())
                .status(Status.INITIATED)
                .build();

        return paymentMapper.toDTO(paymentRepository.save(payment));
    }

    @Override
    public PaymentDTO handlePaymentSuccess(UUID orderId, String razorpayPaymentId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException("Payment not found for Order ID: " + orderId));

        payment.setStatus(Status.SUCCESS);
        payment.setRazorpayPaymentId(razorpayPaymentId);

        // Update order's payment status
        order.setPaymentStatus(Order.PaymentStatus.PAID);
        orderRepository.save(order);

        return paymentMapper.toDTO(paymentRepository.save(payment));
    }

    @Override
    public PaymentDTO handlePaymentFailure(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException("Payment not found for Order ID: " + orderId));

        payment.setStatus(Status.FAILED);
        return paymentMapper.toDTO(paymentRepository.save(payment));
    }
}
