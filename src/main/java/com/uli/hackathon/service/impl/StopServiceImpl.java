package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.repository.StopRepository;
import com.uli.hackathon.service.StopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StopServiceImpl implements StopService {

    private final StopRepository stopRepository;

    @Override
    public Stop getStop(String latitude, String longitude) {
        return stopRepository.getStop(latitude,longitude);
    }

    @Override
    public Stop getOrAddStop(String latitude, String longitude,String name) {
        Stop stop = stopRepository.getStop(latitude,longitude);
        if(stop == null){
            Stop newStop = Stop.builder().stopName(name)
                    .locationLatitude(latitude).locationLongitude(longitude)
                    .build();
            stop = stopRepository.save(newStop);
        }
        return stop;
    }

    @Override
    public Stop getStop(String name) {
        return stopRepository.findByStopName(name);
    }

    @Override
    public Stop getStop(Long stopId) {
        return stopRepository.findById(stopId).orElse(null);
    }

    @Override
    public Stop addStop(Stop stop) {
        return stopRepository.save(stop);
    }
}
