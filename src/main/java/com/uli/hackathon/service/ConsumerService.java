package com.uli.hackathon.service;

import com.uli.hackathon.entity.Consumer;
import com.uli.hackathon.entity.Order;

import java.util.List;

public interface ConsumerService {

    void registerConsumer(Long userId);

    Consumer getConsumer(Long consumerId);
}
