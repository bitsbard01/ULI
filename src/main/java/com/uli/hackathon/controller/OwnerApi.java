package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Visit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/owner")
public interface OwnerApi {

    @GetMapping(value = "/register/{userId}")
    void registerOwner(@PathVariable Long userId);
}
