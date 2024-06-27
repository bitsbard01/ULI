package com.uli.hackathon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "visit")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Visit {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long visitId;

    private LocalDateTime visitStartTime;
    private LocalDateTime visitEndTime;
    private Long availableVolumeCapacity;
    private Long availableWeightCapacity;
    private Double costPerCubicMeter;
    private Double costPerKg;
    private String status;

    @ManyToOne
    @JoinColumn(name = "goods_type_id", nullable = false)
    private GoodsType goodsType;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
}