package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Consumer;
import com.uli.hackathon.entity.Order;
import com.uli.hackathon.entity.User;
import com.uli.hackathon.repository.ConsumerRepository;
import com.uli.hackathon.service.ConsumerService;
import com.uli.hackathon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;

    private final UserService userService;
    @Override
    public void registerConsumer(Long userId) {
        User user = userService.getUser(userId);
        if(user != null){
            Consumer consumer = Consumer.builder().user(user).build();
            consumerRepository.save(consumer);
        }
    }

    @Override
    public Consumer getConsumer(Long consumerId) {
        return consumerRepository.findById(consumerId).orElse(null);
    }

    @Override
    public List<Order> getOrders(Long id, String status) {
        Consumer consumer = consumerRepository.findById(id).orElse(null);
        assert consumer != null;
        if(!StringUtils.hasLength(status)){
            return consumer.getOrders();
        }else{
            List<Order> orders = consumer.getOrders();
            if(StringUtils.hasLength(status)){
                return  orders.stream()
                        .filter(order -> order.getStatus().equalsIgnoreCase(status))
                        .collect(Collectors.toList());
            }else {
                return orders;
            }
        }
    }
}
