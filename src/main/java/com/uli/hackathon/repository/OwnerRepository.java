package com.uli.hackathon.repository;

import com.uli.hackathon.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByUser_UserId(Long userId);
}
