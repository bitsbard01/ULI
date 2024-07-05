package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Vehicle;
import com.uli.hackathon.schemaobjects.VehicleAutoCompleteResponseSo;
import com.uli.hackathon.schemaobjects.VehicleDetailsSo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/vehicle")
public interface VehicleApi {

    @PostMapping(value = "/register")
    ResponseEntity<String> registerVehicle(@RequestBody VehicleDetailsSo vehicleDetailsSo);

    @GetMapping(value = "/autocomplete/{type}")
    List<VehicleAutoCompleteResponseSo> autocompleteVehicleNo(@PathVariable String type);
}
