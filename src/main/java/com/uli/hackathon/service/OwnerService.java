package com.uli.hackathon.service;

import com.uli.hackathon.entity.Owner;

public interface OwnerService {

    void registerOwner(Long userId);

    Owner getOwner(Long ownerId);
}
