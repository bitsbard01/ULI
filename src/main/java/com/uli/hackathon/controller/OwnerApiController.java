package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OwnerApiController implements OwnerApi{

    private final OwnerService ownerService;

    @Override
    public void registerOwner(Long userId) {
        ownerService.registerOwner(userId);
    }
}
