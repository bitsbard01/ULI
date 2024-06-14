package com.uli.hackathon.repository;

import com.uli.hackathon.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long>, RouteCustomRepository {
}
