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
public class OrderResponseSo {

    private Long orderId;
    private LocalDateTime bookingTime;
    private String status;
    private Double cost;
    private String sourceStop;
    private String destinationStop;
}
