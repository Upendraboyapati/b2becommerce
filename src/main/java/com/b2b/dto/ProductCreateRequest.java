package com.b2b.dto;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductCreateRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal costPrice;
    private Integer quantity;
    private UUID categoryId;
    private String imageUrl; // Base64 or URL
}

