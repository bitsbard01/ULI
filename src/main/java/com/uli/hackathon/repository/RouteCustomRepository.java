package com.uli.hackathon.repository;

import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Stop;

public interface RouteCustomRepository {

    Route getRoute(Stop sourceStop, Stop destinationStop, String routeCategory);
}
