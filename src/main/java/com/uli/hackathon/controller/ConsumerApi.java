package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/consumer")
public interface ConsumerApi {

    @PostMapping(value = "/register")
    void registerConsumer(@RequestBody Long userId);

    @GetMapping(value = "/orders/{id}")
    List<Order> getOrders(@PathVariable Long id, @RequestParam String status);

}
