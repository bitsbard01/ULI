package com.uli.hackathon.service;

import com.uli.hackathon.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification addNotification(Notification notification);

    List<Notification> getNotifications(Long userId);
}
