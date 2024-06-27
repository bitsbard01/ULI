package com.uli.hackathon.service;

import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Stop;

import java.util.List;

public interface RouteService {

    Route getOrAddRoute(Stop sourceStop, Stop destinationStop, String routeType);

    List<Route> getRouteBySourceId(Long stopId);

    List<Route> getRouteByDestinationId(Long stopId);

    Route addRoute(Route route);
}
