package com.uli.hackathon.controller;

import com.uli.hackathon.schemaobjects.PersonalInfoRequestSo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owner")
public interface OwnerApi {

    @PostMapping(value = "/register")
    void registerOwner(@RequestBody PersonalInfoRequestSo personalInfoRequestSo);
}
