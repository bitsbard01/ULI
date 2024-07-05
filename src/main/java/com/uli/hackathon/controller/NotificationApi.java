package com.uli.hackathon.controller;

import com.uli.hackathon.schemaobjects.NotificationResponseSo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/notification")
public interface NotificationApi {

    @GetMapping(value = "/search/{userId}")
    List<NotificationResponseSo> getNotification(@PathVariable Long userId);
}
