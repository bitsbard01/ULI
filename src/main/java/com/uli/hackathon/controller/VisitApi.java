package com.uli.hackathon.controller;

import com.uli.hackathon.schemaobjects.TripDetailsSo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/visit")
public interface VisitApi {

    @PostMapping(value = "/add")
    void addVisit(@RequestBody TripDetailsSo tripDetailsSo);
}
