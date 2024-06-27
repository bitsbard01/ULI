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
public class OrderSearchCombinationsRequestSo extends LocationSo {

    private Long consumerId;
    private List<GoodsTypeDetails> goodsTypeDetailsList;
    private LocalDateTime desiredStartTime;
    private LocalDateTime desiredEndTime;
}
