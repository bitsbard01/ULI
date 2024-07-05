package com.uli.hackathon.service;

import com.uli.hackathon.entity.Owner;
import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.schemaobjects.AcceptRejectVisitSo;
import com.uli.hackathon.schemaobjects.OrderSearchCombinationsRequestSo;
import com.uli.hackathon.schemaobjects.TripDetailsSo;
import com.uli.hackathon.schemaobjects.VisitResponseSo;
import com.uli.hackathon.schemaobjects.VisitSequenceDetails;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitService {

    void registerVisit(TripDetailsSo tripDetailsSo);

    Visit addVisit(Visit visit);

    Visit getVisit(Long visitId);

    VisitSequenceDetails getAllVisitIdSequences(OrderSearchCombinationsRequestSo request);

    Visit findVisitWithLeastEndTime(Long routeId, Long goodsTypeId, LocalDateTime startTime, LocalDateTime endTime);

    Visit findVisitWithHighestStartTime(Long routeId, Long goodsTypeId, LocalDateTime startTime, LocalDateTime endTime);

    String acceptRejectVisit(AcceptRejectVisitSo acceptRejectVisitSo);

    List<VisitResponseSo> getVisitsByOwnerAndStatus(Long ownerId, String status);
}
