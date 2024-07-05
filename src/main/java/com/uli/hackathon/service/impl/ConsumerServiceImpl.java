package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Consumer;
import com.uli.hackathon.entity.User;
import com.uli.hackathon.repository.ConsumerRepository;
import com.uli.hackathon.service.ConsumerService;
import com.uli.hackathon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;

    private final UserService userService;
    @Override
    public Consumer registerConsumer(Long userId) {
        User user = userService.getUser(userId);
        if(user != null){
            Consumer consumer1 = consumerRepository.findByUser_UserId(user.getUserId());
            if(consumer1 != null){
                return null;
            }
            Consumer consumer = Consumer.builder().user(user).build();
            return consumerRepository.save(consumer);
        }
        return null;
    }

    @Override
    public Consumer getConsumer(Long consumerId) {
        return consumerRepository.findById(consumerId).orElse(null);
    }

    @Override
    public Consumer getConsumerId(Long userId) {
         return consumerRepository.findByUser_UserId(userId);
    }

}
