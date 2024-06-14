package com.uli.hackathon.schemaobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDetailsSo {

    private String vehicleNumber;
    private String vehicleCategory;
    private LocalDateTime validityStart;
    private LocalDateTime validityEnd;
    private Long ownerId;
}
