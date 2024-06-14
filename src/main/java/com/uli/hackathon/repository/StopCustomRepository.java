package com.uli.hackathon.repository;

import com.uli.hackathon.entity.Stop;

public interface StopCustomRepository {

    Stop getStop(String locationLatitude, String locationLongitude);
}
