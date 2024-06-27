package com.uli.hackathon.service;

import com.uli.hackathon.entity.Consumer;

public interface ConsumerService {

    void registerConsumer(Long userId);

    Consumer getConsumer(Long consumerId);
}
