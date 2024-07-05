package com.uli.hackathon.schemaobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VisitResponseSo {
    private Long visitId;
    private LocalDateTime visitInitiationTime;
    private LocalDateTime visitTerminationTime;
    private Long availableVolumeCapacity;
    private Long availableWeightCapacity;
    private Double costPerCubicMeter;
    private Double costPerKg;
    private String status;
    private String goodsType;
    private String sourceStop;
    private String destinationStop;
    private String vehicle;
}
