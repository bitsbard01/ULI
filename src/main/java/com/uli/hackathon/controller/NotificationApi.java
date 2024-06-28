package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Notification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/notification")
public interface NotificationApi {

    @GetMapping(value = "/search/{userId}")
    List<Notification> getNotification(@PathVariable Long userId);
}
