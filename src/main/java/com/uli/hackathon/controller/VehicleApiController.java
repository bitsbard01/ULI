package com.uli.hackathon.controller;

import com.uli.hackathon.schemaobjects.VehicleDetailsSo;
import com.uli.hackathon.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VehicleApiController implements VehicleApi{

    private final VehicleService vehicleService;

    @Override
    public void registerVehicle(VehicleDetailsSo vehicleDetailsSo) {
        vehicleService.registerVehicle(vehicleDetailsSo);
    }
}
