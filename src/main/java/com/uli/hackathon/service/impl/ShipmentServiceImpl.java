package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Goods;
import com.uli.hackathon.entity.Shipment;
import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.repository.ShipmentRepository;
import com.uli.hackathon.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;


    @Override
    public Shipment addShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    @Override
    public void deleteShipment(Long visitId) {
        shipmentRepository.deleteByVisitId(visitId);
    }

    @Override
    public void deleteShipment(Long goodsId, Long visitId) {
        shipmentRepository.deleteShipmentsByGoodsIdExceptVisitId(goodsId,visitId);
    }

    @Override
    public List<Long> getCancelVisitsByGoodsIdExceptVisitId(Long goodsId, Long visitId) {
        return shipmentRepository.findVisitIdsByGoodsIdExceptVisitId(goodsId, visitId);
    }

    @Override
    public Shipment findByVisit(Visit visit) {
        return shipmentRepository.findByVisit(visit);
    }

    @Override
    public List<Visit> getVisits(Goods goods) {
        return shipmentRepository.findVisitsByGoods(goods);
    }
}
