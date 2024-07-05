package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Consumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/consumer")
public interface ConsumerApi {

    @GetMapping(value = "/register/{userId}")
    Consumer registerConsumer(@PathVariable Long userId);

    @GetMapping(value = "/consumer-id/{userId}")
    Consumer getConsumerId(@PathVariable Long userId);
}
