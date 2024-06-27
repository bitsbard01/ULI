package com.uli.hackathon.controller;

import com.uli.hackathon.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserApi {

    @PostMapping("/register")
    User registerUser(@RequestBody User user);

    @PostMapping("/login")
    User loginUser(@RequestBody User user);
}
