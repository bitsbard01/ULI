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
public class GoodsTypeDetails {

    private String goodsType;
    private Long volumeCapacity;
    private Long weightCapacity;
    private Double chargePerCubicMeter;
    private Double chargePerKg;
}
