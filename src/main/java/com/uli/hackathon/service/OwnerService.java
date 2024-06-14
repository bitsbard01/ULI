package com.uli.hackathon.service;

import com.uli.hackathon.entity.Owner;
import com.uli.hackathon.schemaobjects.PersonalInfoRequestSo;

public interface OwnerService {

    void registerOwner(PersonalInfoRequestSo personalInfoRequestSo);

    Owner getOwner(Long ownerId);
}
