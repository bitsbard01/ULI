package com.uli.hackathon.service;

import com.uli.hackathon.entity.Goods;
import com.uli.hackathon.entity.Shipment;
import com.uli.hackathon.entity.Visit;

import java.util.List;

public interface ShipmentService {

    Shipment addShipment(Shipment shipment);

    void deleteShipment(Long visitId);

    void deleteShipment(Long goodsId,Long visitId);

    List<Long> getCancelVisitsByGoodsIdExceptVisitId(Long goodsId, Long visitId);
    Shipment findByVisit(Visit visit);

    List<Visit> getVisits(Goods goods);
}
