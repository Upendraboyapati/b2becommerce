package com.b2b.service.impl;

import com.b2b.dto.NotificationDTO;
import com.b2b.entity.NotificationLog;
import com.b2b.entity.NotificationLog.Type;
import com.b2b.entity.User;
import com.b2b.mapper.NotificationMapper;
import com.b2b.repository.NotificationLogRepository;
import com.b2b.repository.UserRepository;
import com.b2b.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationLogRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void sendOrderUpdate(UUID userId, String subject, String message) {
        createNotification(userId, subject, message, Type.ORDER_UPDATE);
    }

    @Override
    public void sendInventoryAlert(UUID userId, String subject, String message) {
        createNotification(userId, subject, message, Type.INVENTORY_ALERT);
    }

    @Override
    public List<NotificationDTO> getNotificationsForUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return notificationRepository.findByUser(user).stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void createNotification(UUID userId, String subject, String message, Type type) {
        User user = userRepository.findById(userId).orElseThrow();
        NotificationLog notification = NotificationLog.builder()
                .user(user)
                .subject(subject)
                .message(message)
                .type(type)
                .Read(false)
                .build();
        notificationRepository.save(notification);
    }
}
