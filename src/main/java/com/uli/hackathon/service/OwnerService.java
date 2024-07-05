package com.uli.hackathon.service;

import com.uli.hackathon.entity.Owner;
import com.uli.hackathon.entity.Visit;

import java.util.List;

public interface OwnerService {

    Owner registerOwner(Long userId);

    Owner getOwner(Long ownerId);

    Owner getOwnerId(Long userId);
}
