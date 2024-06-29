package com.uli.hackathon.repository;

import com.uli.hackathon.entity.Consumer;
import com.uli.hackathon.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByConsumerConsumerIdAndStatus(Long consumerId, String status);
    List<Order> findByConsumerConsumerId(Long consumerId);
}
