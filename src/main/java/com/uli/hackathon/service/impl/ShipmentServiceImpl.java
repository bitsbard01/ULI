package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Shipment;
import com.uli.hackathon.repository.ShipmentRepository;
import com.uli.hackathon.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;


    @Override
    public Shipment addShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }
}
