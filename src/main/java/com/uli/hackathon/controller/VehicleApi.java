package com.uli.hackathon.controller;

import com.uli.hackathon.schemaobjects.VehicleDetailsSo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/vehicle")
public interface VehicleApi {

    @PostMapping(value = "/register")
    void registerVehicle(@RequestBody VehicleDetailsSo vehicleDetailsSo);
}
