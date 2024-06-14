package com.uli.hackathon.schemaobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlaceRequestSo extends LocationSo {

    private Long consumerId;
    private Double cost;
    private List<OrderDetailsSo> orderDetailsSoList;
}
