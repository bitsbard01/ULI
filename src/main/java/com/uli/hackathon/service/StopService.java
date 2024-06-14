package com.uli.hackathon.service;

import com.uli.hackathon.entity.Stop;

public interface StopService {

    Stop getStop(String latitude, String longitude);

    Stop addStop(Stop stop);
}
