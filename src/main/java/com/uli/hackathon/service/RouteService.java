package com.uli.hackathon.service;

import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Stop;

public interface RouteService {

    Route getRoute(Stop sourceStop, Stop destinationStop, String routeType);

    Route addRoute(Route route);
}
