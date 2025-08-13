package com.b2b.controller;

import com.b2b.dto.PaymentDTO;
import com.b2b.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // POST: Initiate Payment
    @PostMapping("/initiate/{orderId}")
    public ResponseEntity<PaymentDTO> initiatePayment(@PathVariable UUID orderId) {
        return ResponseEntity.ok(paymentService.initiatePayment(orderId));
    }

    // POST: Handle Success
    @PostMapping("/success/{orderId}")
    public ResponseEntity<PaymentDTO> handleSuccess(
            @PathVariable UUID orderId,
            @RequestParam String razorpayPaymentId) {
        return ResponseEntity.ok(paymentService.handlePaymentSuccess(orderId, razorpayPaymentId));
    }

    // POST: Handle Failure
    @PostMapping("/failure/{orderId}")
    public ResponseEntity<PaymentDTO> handleFailure(@PathVariable UUID orderId) {
        return ResponseEntity.ok(paymentService.handlePaymentFailure(orderId));
    }
}
