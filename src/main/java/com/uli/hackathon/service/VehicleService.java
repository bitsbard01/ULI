package com.uli.hackathon.service;

import com.uli.hackathon.entity.Vehicle;
import com.uli.hackathon.schemaobjects.VehicleDetailsSo;

public interface VehicleService {

    void registerVehicle(VehicleDetailsSo vehicleDetailsSo);

    Vehicle getVehicle(Long vehicleId);
}
