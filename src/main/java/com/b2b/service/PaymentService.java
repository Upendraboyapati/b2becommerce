package com.b2b.service;

import com.b2b.dto.PaymentDTO;

import java.util.UUID;

public interface PaymentService {
    PaymentDTO initiatePayment(UUID orderId);
    PaymentDTO handlePaymentSuccess(UUID orderId, String razorpayPaymentId);
    PaymentDTO handlePaymentFailure(UUID orderId);
}
