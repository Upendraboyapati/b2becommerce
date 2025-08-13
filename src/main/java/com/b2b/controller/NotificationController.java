package com.b2b.controller;

import com.b2b.dto.NotificationDTO;
import com.b2b.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsForUser(@PathVariable UUID userId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/order-update")
    public ResponseEntity<Void> sendOrderUpdate(@RequestParam UUID userId,
                                                @RequestParam String subject,
                                                @RequestParam String message) {
        notificationService.sendOrderUpdate(userId, subject, message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/inventory-alert")
    public ResponseEntity<Void> sendInventoryAlert(@RequestParam UUID userId,
                                                   @RequestParam String subject,
                                                   @RequestParam String message) {
        notificationService.sendInventoryAlert(userId, subject, message);
        return ResponseEntity.ok().build();
    }
}
