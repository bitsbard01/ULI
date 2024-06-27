package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Notification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/notification")
public interface NotificationApi {

    @PostMapping(value = "/search")
    List<Notification> getNotification(@RequestBody Long userId);
}
