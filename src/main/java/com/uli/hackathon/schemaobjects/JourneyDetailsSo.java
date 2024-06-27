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
public class JourneyDetailsSo extends LocationSo{
    private Long visitId;
    private LocalDateTime visitInitiationTime;
    private LocalDateTime visitTerminationTime;
    private Long vehicleId;
    private String vehicleCategory;
    private Double cost;
}
