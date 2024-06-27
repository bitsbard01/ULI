package com.uli.hackathon.schemaobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchCombinationsResponseSo {

    private Map<String, List<List<JourneyDetailsSo>>> goodsTypeJourneyListMap;
    private String orderAvailabilityStatus;
    private Long sourceStopId;
    private Long destinationStopId;
}
