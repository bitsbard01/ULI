package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Visit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/owner")
public interface OwnerApi {

    @PostMapping(value = "/register")
    void registerOwner(@RequestBody Long userId);

    @GetMapping(value = "/visits/{id}")
    List<Visit> getVisits(@PathVariable Long id, @RequestParam String status);
}
