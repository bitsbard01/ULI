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
    private String sourceLatitude;
    private String sourceLongitude;
    private String sourceName;
    private String destinationLatitude;
    private String destinationLongitude;
    private String destinationName;
}
