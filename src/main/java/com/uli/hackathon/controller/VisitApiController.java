package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.schemaobjects.AcceptRejectVisitSo;
import com.uli.hackathon.schemaobjects.TripDetailsSo;
import com.uli.hackathon.schemaobjects.VisitResponseSo;
import com.uli.hackathon.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VisitApiController implements VisitApi{

    private final VisitService visitService;

    @Override
    public void registerVisit(TripDetailsSo tripDetailsSo) {
        visitService.registerVisit(tripDetailsSo);
    }

    @Override
    public ResponseEntity<String> acceptVisit(AcceptRejectVisitSo acceptRejectVisitSo) {
        String message = visitService.acceptRejectVisit(acceptRejectVisitSo);
        return ResponseEntity.ok(message);
    }

    @Override
    public List<VisitResponseSo> getVisits(Long ownerId, String status) {
        return visitService.getVisitsByOwnerAndStatus(ownerId,status);
    }
}
