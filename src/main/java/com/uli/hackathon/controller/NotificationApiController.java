package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Notification;
import com.uli.hackathon.schemaobjects.NotificationResponseSo;
import com.uli.hackathon.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NotificationApiController implements NotificationApi{

    private final NotificationService notificationService;

    @Override
    public List<NotificationResponseSo> getNotification(Long userId) {
        return notificationService.getNotifications(userId);
    }
}
