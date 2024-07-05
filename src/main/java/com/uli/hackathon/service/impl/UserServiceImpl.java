package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.User;
import com.uli.hackathon.repository.UserRepository;
import com.uli.hackathon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        User user1 = userRepository.findByUserName(user.getUserName());
        if(user1 != null){
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String userName, String password) {
        User user = userRepository.findByUserName(userName);
        if (user != null && StringUtils.hasLength(password) && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
