package com.uli.hackathon.repository;

import com.uli.hackathon.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long>,StopCustomRepository {
}
