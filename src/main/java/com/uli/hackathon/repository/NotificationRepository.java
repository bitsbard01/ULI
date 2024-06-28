package com.uli.hackathon.repository;


import com.uli.hackathon.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser_UserId(Long userId);
}
