package com.uli.hackathon.service;

import com.uli.hackathon.entity.Stop;

import java.util.List;

public interface StopService {

    Stop getOrAddStop(Double latitude, Double  longitude, String name);

    Stop getStop(Long stopId);

    List<Stop> getStopsByName(String stopName);
}
