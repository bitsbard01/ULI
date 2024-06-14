package com.uli.hackathon.controller;

import com.uli.hackathon.schemaobjects.PersonalInfoRequestSo;
import com.uli.hackathon.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OwnerApiController implements OwnerApi{

    private final OwnerService ownerService;

    @Override
    public void registerOwner(PersonalInfoRequestSo personalInfoRequestSo) {
        ownerService.registerOwner(personalInfoRequestSo);
    }
}
