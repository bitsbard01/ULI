package com.uli.hackathon.service;

import com.uli.hackathon.entity.User;

public interface UserService {

    User registerUser(User user);

    User loginUser(String userName, String password);

    User getUser(Long userId);
}
