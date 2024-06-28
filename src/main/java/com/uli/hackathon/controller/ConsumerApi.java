package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/consumer")
public interface ConsumerApi {

    @GetMapping(value = "/register/{userId}")
    void registerConsumer(@PathVariable Long userId);

}
