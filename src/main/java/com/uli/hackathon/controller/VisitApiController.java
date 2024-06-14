package com.uli.hackathon.controller;


import com.uli.hackathon.schemaobjects.TripDetailsSo;
import com.uli.hackathon.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VisitApiController implements VisitApi{

    private final VisitService visitService;

    @Override
    public void addVisit(TripDetailsSo tripDetailsSo) {
        visitService.addVisit(tripDetailsSo);
    }
}
