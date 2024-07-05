package com.uli.hackathon.controller;
import com.uli.hackathon.entity.Consumer;
import com.uli.hackathon.service.impl.ConsumerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ConsumerApiController implements ConsumerApi{

    private final ConsumerServiceImpl consumerService;

    @Override
    public Consumer registerConsumer(Long userId) {
        return consumerService.registerConsumer(userId);
    }

    @Override
    public Consumer getConsumerId(Long userId) {
        return consumerService.getConsumerId(userId);
    }
}
