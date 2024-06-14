package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.GoodsType;
import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.entity.Vehicle;
import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.repository.VisitRepository;
import com.uli.hackathon.schemaobjects.TripDetailsSo;
import com.uli.hackathon.service.GoodsTypeService;
import com.uli.hackathon.service.VehicleService;
import com.uli.hackathon.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    private final StopServiceImpl stopService;

    private final RouteServiceImpl routeService;
    private final GoodsTypeService goodsTypeService;

    private final VehicleService vehicleService;
    @Override
    public void addVisit(TripDetailsSo tripDetailsSo) {
        Stop sourceStop = stopService.getStop(tripDetailsSo.getSourceLatitude(),tripDetailsSo.getSourceLongitude());
        if(sourceStop == null){
            Stop stop = Stop.builder().stopName(tripDetailsSo.getSourceName())
                    .locationLatitude(tripDetailsSo.getSourceLatitude()).locationLongitude(tripDetailsSo.getSourceLongitude())
                    .build();
            sourceStop = stopService.addStop(stop);
        }
        Stop destinationStop = stopService.getStop(tripDetailsSo.getDestinationLatitude(),tripDetailsSo.getDestinationLongitude());
        if(destinationStop == null){
            Stop stop = Stop.builder().stopName(tripDetailsSo.getDestinationName())
                    .locationLatitude(tripDetailsSo.getDestinationLatitude()).locationLongitude(tripDetailsSo.getDestinationLongitude())
                    .build();
            destinationStop = stopService.addStop(stop);
        }

        Vehicle vehicle = vehicleService.getVehicle(tripDetailsSo.getVehicleId());
        if(vehicle == null){
            throw new RuntimeException("Vehicle not found with id: " + tripDetailsSo.getVehicleId());
        }

        Route route = routeService.getRoute(sourceStop,destinationStop,vehicle.getVehicleType());
        if(route == null){
            route = Route.builder().sourceStop(sourceStop).destinationStop(destinationStop)
                    .routeType(vehicle.getVehicleType()).distance(tripDetailsSo.getDistance()).build();
            route = routeService.addRoute(route);
        }
        Route finalRoute = route;
        tripDetailsSo.getGoodsTypeDetailsList().forEach(goodsTypeDetails -> {
            GoodsType goodsType = goodsTypeService.getGoodsType(goodsTypeDetails.getGoodsType());
            Visit visit = Visit.builder().visitStartTime(tripDetailsSo.getVisitInitiationTime())
                    .visitEndTime(tripDetailsSo.getVisitTerminationTime()).route(finalRoute).goodsType(goodsType).vehicle(vehicle)
                    .availableVolumeCapacity(goodsTypeDetails.getVolumeCapacity())
                    .availableWeightCapacity(goodsTypeDetails.getWeightCapacity())
                    .costPerCubicMeter(goodsTypeDetails.getChargePerCubicMeter()).costPerKg(goodsTypeDetails.getChargePerKg())
                    .build();
            visitRepository.save(visit);
        });
    }

    @Override
    public Visit getVisit(Long visitId) {
        return visitRepository.findById(visitId).orElse(null);
    }
}
