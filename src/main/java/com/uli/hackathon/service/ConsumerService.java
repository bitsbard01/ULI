package com.uli.hackathon.service;

import com.uli.hackathon.entity.Consumer;
import com.uli.hackathon.schemaobjects.PersonalInfoRequestSo;

public interface ConsumerService {

    void registerConsumer(PersonalInfoRequestSo personalInfoRequestSo);

    Consumer getConsumer(Long consumerId);
}
