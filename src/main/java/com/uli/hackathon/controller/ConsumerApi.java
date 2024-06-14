package com.uli.hackathon.controller;

import com.uli.hackathon.schemaobjects.PersonalInfoRequestSo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/consumer")
public interface ConsumerApi {

    @PostMapping(value = "/register")
    void registerConsumer(@RequestBody PersonalInfoRequestSo personalInfoRequestSo);

}
