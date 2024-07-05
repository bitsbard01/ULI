package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.schemaobjects.AcceptRejectVisitSo;
import com.uli.hackathon.schemaobjects.TripDetailsSo;
import com.uli.hackathon.schemaobjects.VisitResponseSo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/visit")
public interface VisitApi {

    @PostMapping(value = "/register")
    void registerVisit(@RequestBody TripDetailsSo tripDetailsSo);

    @PostMapping(value = "/accept-reject")
    ResponseEntity<String> acceptVisit(@RequestBody AcceptRejectVisitSo acceptRejectVisitSo);

    @GetMapping(value = "/visits/{ownerId}")
    List<VisitResponseSo> getVisits(@PathVariable Long ownerId, @RequestParam(required = false) String status);

}
