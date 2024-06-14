package com.uli.hackathon.schemaobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TripDetailsSo extends LocationSo {

    private LocalDateTime visitInitiationTime;
    private LocalDateTime visitTerminationTime;
    private Long vehicleId;
    private Double distance;
    private List<GoodsTypeDetails> goodsTypeDetailsList;
}
