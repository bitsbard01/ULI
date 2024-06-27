package com.uli.hackathon.service;

import com.uli.hackathon.entity.Stop;

public interface StopService {

    Stop getStop(String latitude, String longitude);
    Stop getOrAddStop(String latitude, String longitude, String name);

    Stop getStop(String name);

    Stop getStop(Long stopId);
    Stop addStop(Stop stop);
}
