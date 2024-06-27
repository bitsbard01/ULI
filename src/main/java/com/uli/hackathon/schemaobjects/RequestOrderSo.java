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
public class RequestOrderSo{
    private Long consumerId;
    private List<GoodsTypeDetails> goodsTypeDetailsList;
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime endTime = LocalDateTime.now().plusYears(1);
    private Long sourceStopId;
    private Long destinationStopId;
}
