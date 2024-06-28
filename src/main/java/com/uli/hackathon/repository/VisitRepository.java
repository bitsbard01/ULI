package com.uli.hackathon.repository;

import com.uli.hackathon.entity.GoodsType;
import com.uli.hackathon.entity.Visit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("SELECT v FROM Visit v WHERE v.goodsType = :goodsType " +
            "AND v.visitStartTime >= :startTime " +
            "AND v.visitEndTime <= :endTime " +
            "AND v.status = :status " +
            "AND v.availableVolumeCapacity >= :volume " +
            "AND v.availableWeightCapacity >= :weight")
    List<Visit> findAllByGoodsTypeAndTimeRangeAndVolumeAndWeight(
            @Param("goodsType") GoodsType goodsType,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("status") String status,
            @Param("volume") Long volume,
            @Param("weight") Long weight
    );


    @Query("SELECT v FROM Visit v WHERE v.route.routeId = :routeId AND v.goodsType.goodsTypeId = :goodsTypeId AND v.visitStartTime > :startTime AND v.visitEndTime < :endTime ORDER BY v.visitEndTime ASC")
    List<Visit> findFirstByRouteAndVisitStartTimeAfterAndVisitEndTimeBefore(
            @Param("routeId") Long routeId,
            @Param("goodsTypeId") Long goodsTypeId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable);

    @Query("SELECT v FROM Visit v WHERE v.route.routeId = :routeId AND v.goodsType.goodsTypeId = :goodsTypeId AND v.visitStartTime > :startTime AND v.visitEndTime < :endTime ORDER BY v.visitStartTime DESC")
    List<Visit> findFirstByRouteAndVisitStartTimeAfterAndVisitEndTimeBeforeOrderByVisitStartTimeDesc(
            @Param("routeId") Long routeId,
            @Param("goodsTypeId") Long goodsTypeId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable);
}
