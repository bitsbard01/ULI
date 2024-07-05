package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Vehicle;
import com.uli.hackathon.schemaobjects.VehicleAutoCompleteResponseSo;
import com.uli.hackathon.schemaobjects.VehicleDetailsSo;
import com.uli.hackathon.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VehicleApiController implements VehicleApi{

    private final VehicleService vehicleService;

    @Override
    public ResponseEntity<String> registerVehicle(VehicleDetailsSo vehicleDetailsSo) {
        Vehicle vehicle = vehicleService.registerVehicle(vehicleDetailsSo);
        if(vehicle!=null){
            return ResponseEntity.ok("Registered successfully");
        }else{
            return ResponseEntity.ok("Error occurred");
        }
    }

    @GetMapping("/autocomplete/{type}")
    public List<VehicleAutoCompleteResponseSo> autocompleteVehicleNo(@PathVariable String type) {
        return vehicleService.findVehicleByKeyword(type);
    }
}
