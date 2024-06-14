package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Owner;
import com.uli.hackathon.entity.Vehicle;
import com.uli.hackathon.repository.VehicleRepository;
import com.uli.hackathon.schemaobjects.VehicleDetailsSo;
import com.uli.hackathon.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final OwnerServiceImpl ownerService;

    private final VehicleRepository vehicleRepository;

    @Override
    public void registerVehicle(VehicleDetailsSo vehicleDetailsSo) {
        Owner owner = ownerService.getOwner(vehicleDetailsSo.getOwnerId());
        if(owner == null){
            throw new RuntimeException("Owner not found with id: " + vehicleDetailsSo.getOwnerId());
        }

        Vehicle vehicle = Vehicle.builder().vehicleNo(vehicleDetailsSo.getVehicleNumber())
                .vehicleType(vehicleDetailsSo.getVehicleCategory()).validFrom(vehicleDetailsSo.getValidityStart())
                .validTill(vehicleDetailsSo.getValidityEnd()).owner(owner)
                .build();

        vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle getVehicle(Long vehicleId) {
        return vehicleRepository.findById(vehicleId).orElse(null);
    }
}
