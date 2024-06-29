package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Owner;
import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.entity.Vehicle;
import com.uli.hackathon.repository.VehicleRepository;
import com.uli.hackathon.schemaobjects.LocationSo;
import com.uli.hackathon.schemaobjects.VehicleDetailsSo;
import com.uli.hackathon.service.RouteService;
import com.uli.hackathon.service.StopService;
import com.uli.hackathon.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uli.hackathon.utils.Constants.THRESHOLD_DISTANCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final OwnerServiceImpl ownerService;

    private final StopService stopService;

    private final RouteService routeService;
    private final VehicleRepository vehicleRepository;

    @Override
    public void registerVehicle(VehicleDetailsSo vehicleDetailsSo) {
        Owner owner = ownerService.getOwner(vehicleDetailsSo.getOwnerId());
        if(owner == null){
            throw new RuntimeException("Owner not found with id: " + vehicleDetailsSo.getOwnerId());
        }

        LocationSo locationSo = vehicleDetailsSo.getLocationSo();
        Stop sourceStop = stopService.getOrAddStop(locationSo.getSourceLatitude(),locationSo.getSourceLongitude()
                ,locationSo.getSourceName());
        Stop destinationStop = stopService.getOrAddStop(locationSo.getDestinationLatitude(),locationSo.getDestinationLongitude()
                ,locationSo.getDestinationName());
        Route route = routeService.getOrAddRoute(sourceStop,destinationStop,vehicleDetailsSo.getVehicleCategory());

        Vehicle vehicle = Vehicle.builder().vehicleNo(vehicleDetailsSo.getVehicleNumber())
                .vehicleType(vehicleDetailsSo.getVehicleCategory()).validFrom(vehicleDetailsSo.getValidityStart())
                .validTill(vehicleDetailsSo.getValidityEnd()).owner(owner).mostFrequentRoute(route)
                .build();

        vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle getVehicle(Long vehicleId) {
        return vehicleRepository.findById(vehicleId).orElse(null);
    }

    public List<Vehicle> findVehiclesByRoute(Route route) {
        double sourceLat = route.getSourceStop().getLocationLatitude();
        double sourceLng = route.getSourceStop().getLocationLongitude();
        double destLat = route.getDestinationStop().getLocationLatitude();
        double destLng = route.getDestinationStop().getLocationLongitude();

        List<Vehicle> vehicles = vehicleRepository.findVehiclesByRouteProximity(sourceLat, sourceLng, destLat, destLng
                , THRESHOLD_DISTANCE);

        if (vehicles.isEmpty()) {
            PageRequest pageRequest = PageRequest.of(0, 3);
            vehicles = vehicleRepository.findNearestVehicles(sourceLat, sourceLng, destLat, destLng,pageRequest);
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByOwner(Owner owner) {
        return vehicleRepository.findByOwner(owner);
    }
}
