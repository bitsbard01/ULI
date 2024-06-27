package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Notification;
import com.uli.hackathon.repository.NotificationRepository;
import com.uli.hackathon.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}
