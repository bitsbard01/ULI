package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Consumer;
import com.uli.hackathon.repository.ConsumerRepository;
import com.uli.hackathon.schemaobjects.PersonalInfoRequestSo;
import com.uli.hackathon.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;
    @Override
    public void registerConsumer(PersonalInfoRequestSo personalInfoRequestSo) {
        Consumer consumer = Consumer.builder().name(personalInfoRequestSo.getUserName())
                .email(personalInfoRequestSo.getUserEmail()).address(personalInfoRequestSo.getUserAddress())
                .bankAccountNo(personalInfoRequestSo.getUserBankAccountNo())
                .phoneNo(personalInfoRequestSo.getUserPhoneNo()).build();
        consumerRepository.save(consumer);
    }

    @Override
    public Consumer getConsumer(Long consumerId) {
        return consumerRepository.findById(consumerId).orElse(null);
    }
}
