package com.uli.hackathon.controller;

import com.uli.hackathon.entity.User;
import com.uli.hackathon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserApiController implements UserApi{

    private final UserService userService;

    @Override
    public User registerUser(User user) {
        return userService.registerUser(user);
    }

    @Override
    public User loginUser(User user) {
        return userService.loginUser(user.getUserName(), user.getPassword());
    }
}
