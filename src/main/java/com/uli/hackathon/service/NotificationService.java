package com.uli.hackathon.service;

import com.uli.hackathon.entity.Notification;
import com.uli.hackathon.schemaobjects.NotificationResponseSo;

import java.util.List;

public interface NotificationService {

    Notification addNotification(Notification notification);

    List<NotificationResponseSo> getNotifications(Long userId);
}
