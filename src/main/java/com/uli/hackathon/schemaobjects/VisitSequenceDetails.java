package com.uli.hackathon.schemaobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitSequenceDetails {

    private Long sourceStopId;
    private Long destinationStopId;
    private Map<String, List<List<Long>>> visitIdSequences;
}
