package com.uli.hackathon.repository;

import com.uli.hackathon.entity.Goods;
import com.uli.hackathon.entity.Shipment;
import com.uli.hackathon.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Shipment s WHERE s.visit.visitId = :visitId")
    void deleteByVisitId(@Param("visitId") Long visitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Shipment s WHERE s.goods.goodsId = :goodsId AND s.visit.visitId <> :visitId")
    void deleteShipmentsByGoodsIdExceptVisitId(@Param("goodsId") Long goodsId, @Param("visitId") Long visitId);

    @Query("SELECT s.visit.visitId FROM Shipment s WHERE s.goods.goodsId = :goodsId AND s.visit.visitId <> :visitId")
    List<Long> findVisitIdsByGoodsIdExceptVisitId(@Param("goodsId") Long goodsId, @Param("visitId") Long visitId);
    Shipment findByVisit(Visit visit);

    @Query("SELECT s.visit FROM Shipment s WHERE s.goods = :goods")
    List<Visit> findVisitsByGoods(@Param("goods") Goods goods);
}
