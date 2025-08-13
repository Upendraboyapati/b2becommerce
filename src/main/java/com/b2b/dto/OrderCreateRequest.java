package com.b2b.dto;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
public class OrderCreateRequest {
    private List<OrderItemRequest> items;
    private UUID sellerId;
	public UUID getBuyerId() {
		// TODO Auto-generated method stub
		return null;
	}
}


