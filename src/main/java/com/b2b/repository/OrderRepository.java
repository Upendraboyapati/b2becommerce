package com.b2b.repository;

import com.b2b.entity.Order;
import com.b2b.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByBuyer(User buyer);
    List<Order> findBySeller(User seller);
}
