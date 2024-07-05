package com.uli.hackathon.repository;


import com.uli.hackathon.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long>{
    Consumer findByUser_UserId(Long userId);
}
