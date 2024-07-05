package com.uli.hackathon.controller;

import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.service.StopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StopApiController implements StopApi{

    private final StopService stopService;

    public List<Stop> autocompleteStopName(@PathVariable String stopName) {
        return stopService.getStopsByName(stopName);
    }

    @Override
    public Stop addStop(Stop stop) {
        return stopService.getOrAddStop(stop.getLocationLatitude(),stop.getLocationLongitude(),stop.getStopName());
    }
}
