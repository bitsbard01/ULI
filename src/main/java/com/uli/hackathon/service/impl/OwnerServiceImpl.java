package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Order;
import com.uli.hackathon.entity.Owner;
import com.uli.hackathon.entity.User;
import com.uli.hackathon.entity.Vehicle;
import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.repository.OwnerRepository;
import com.uli.hackathon.service.OwnerService;
import com.uli.hackathon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    private final UserService userService;
    @Override
    public void registerOwner(Long userId) {
        User user = userService.getUser(userId);
        Owner owner = Owner.builder().user(user).build();
        ownerRepository.save(owner);
    }

    @Override
    public Owner getOwner(Long ownerId) {
        return ownerRepository.findById(ownerId).orElse(null);
    }
}
