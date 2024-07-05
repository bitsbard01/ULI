package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.repository.StopRepository;
import com.uli.hackathon.service.StopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StopServiceImpl implements StopService {

    private final StopRepository stopRepository;

    @Override
    public Stop getOrAddStop(Double latitude, Double longitude, String name) {
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
    public Stop getStop(Long stopId) {
        return stopRepository.findById(stopId).orElse(null);
    }

    public List<Stop> getStopsByName(String stopName) {
        return stopRepository.findByStopNameContainingIgnoreCase(stopName);
    }
}
