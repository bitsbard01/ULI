package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Notification;
import com.uli.hackathon.repository.NotificationRepository;
import com.uli.hackathon.schemaobjects.NotificationResponseSo;
import com.uli.hackathon.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<NotificationResponseSo> getNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByUser_UserId(userId);
        return notifications.stream().map(notification -> NotificationResponseSo.builder()
                .notificationId(notification.getNotificationId()).type(notification.getType())
                .details(notification.getDetails()).status(notification.getStatus()).timestamp(notification.getTimestamp())
                .build()).collect(Collectors.toList());
    }
}
