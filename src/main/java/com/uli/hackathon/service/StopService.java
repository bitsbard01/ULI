package com.uli.hackathon.service;

import com.uli.hackathon.entity.Stop;

public interface StopService {

    Stop getOrAddStop(Double latitude, Double  longitude, String name);

    Stop getStop(Long stopId);
}
