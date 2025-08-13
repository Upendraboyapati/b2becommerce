package com.b2b.repository;

import com.b2b.entity.Payment;
import com.b2b.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByOrder(Order order);
}
