package com.uli.hackathon.schemaobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LocationSo {
    private Double sourceLatitude;
    private Double sourceLongitude;
    private String sourceName;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private String destinationName;
}
