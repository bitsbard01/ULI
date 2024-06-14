package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.repository.RouteRepository;
import com.uli.hackathon.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Override
    public Route getRoute(Stop sourceStop, Stop destinationStop, String routeType) {
        return routeRepository.getRoute(sourceStop,destinationStop,routeType);
    }

    @Override
    public Route addRoute(Route route) {
        return routeRepository.save(route);
    }
}
