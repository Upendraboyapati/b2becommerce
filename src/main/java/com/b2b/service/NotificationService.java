package com.b2b.service;

import com.b2b.dto.NotificationDTO;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    void sendOrderUpdate(UUID userId, String subject, String message);
    void sendInventoryAlert(UUID userId, String subject, String message);
    List<NotificationDTO> getNotificationsForUser(UUID userId);
}
