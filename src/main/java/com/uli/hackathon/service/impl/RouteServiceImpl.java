package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.helper.CommonHelper;
import com.uli.hackathon.repository.RouteRepository;
import com.uli.hackathon.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    private final CommonHelper commonHelper;
    @Override
    public Route getOrAddRoute(Stop sourceStop, Stop destinationStop, String routeType) {
        Route route = routeRepository.getRoute(sourceStop,destinationStop,routeType);
        if(route == null){
            Double distance = commonHelper.calculateDistance(sourceStop.getLocationLatitude(),sourceStop.getLocationLongitude()
                    ,destinationStop.getLocationLatitude(),destinationStop.getLocationLongitude());
            Route newRoute = Route.builder().sourceStop(sourceStop).destinationStop(destinationStop)
                    .routeType(routeType).distance(distance).build();
            route = routeRepository.save(newRoute);
        }
        return route;
    }

    @Override
    public List<Route>  getRouteBySourceId(Long stopId) {
        return  routeRepository.findBySourceStopStopId(stopId);
    }

    @Override
    public List<Route> getRouteByDestinationId(Long stopId) {
        return routeRepository.findByDestinationStopStopId(stopId);
    }

    @Override
    public Route addRoute(Route route) {
        return routeRepository.save(route);
    }
}
