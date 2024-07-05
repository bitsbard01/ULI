package com.uli.hackathon.service;

import com.uli.hackathon.entity.Owner;
import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Vehicle;
import com.uli.hackathon.schemaobjects.VehicleAutoCompleteResponseSo;
import com.uli.hackathon.schemaobjects.VehicleDetailsSo;

import java.util.List;

public interface VehicleService {

    Vehicle registerVehicle(VehicleDetailsSo vehicleDetailsSo);

    Vehicle getVehicle(Long vehicleId);

    List<Vehicle> findVehiclesByRoute(Route route);

    List<Vehicle> getVehiclesByOwner(Owner owner);

    List<VehicleAutoCompleteResponseSo> findVehicleByKeyword(String keyword);
}
