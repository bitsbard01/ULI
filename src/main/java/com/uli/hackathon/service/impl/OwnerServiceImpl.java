package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Owner;
import com.uli.hackathon.repository.OwnerRepository;
import com.uli.hackathon.schemaobjects.PersonalInfoRequestSo;
import com.uli.hackathon.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    @Override
    public void registerOwner(PersonalInfoRequestSo personalInfoRequestSo) {
        Owner owner = Owner.builder().name(personalInfoRequestSo.getUserName())
                .email(personalInfoRequestSo.getUserEmail()).address(personalInfoRequestSo.getUserAddress())
                .bankAccountNo(personalInfoRequestSo.getUserBankAccountNo())
                .phoneNo(personalInfoRequestSo.getUserPhoneNo()).build();
        ownerRepository.save(owner);
    }

    @Override
    public Owner getOwner(Long ownerId) {
        return ownerRepository.findById(ownerId).orElse(null);
    }
}
