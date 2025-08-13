package com.b2b.repository;

import com.b2b.entity.NotificationLog;
import com.b2b.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, UUID> {
    List<NotificationLog> findByUser(User user);
}
