package com.b2b.dto;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String businessName;
    private String address;
    private String role; // "USER" or "ADMIN"
    private boolean isBlocked;
    private LocalDateTime createdAt;
}

