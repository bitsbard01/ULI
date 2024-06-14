package com.uli.hackathon.repository;

import com.uli.hackathon.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
