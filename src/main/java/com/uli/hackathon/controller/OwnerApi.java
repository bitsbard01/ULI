package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Owner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/owner")
public interface OwnerApi {

    @GetMapping(value = "/register/{userId}")
    Owner registerOwner(@PathVariable Long userId);

    @GetMapping(value = "/owner-id/{userId}")
    Owner getOwnerId(@PathVariable Long userId);
}
