package com.uli.hackathon.service;

import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.schemaobjects.TripDetailsSo;

public interface VisitService {

    void addVisit(TripDetailsSo tripDetailsSo);

    Visit getVisit(Long visitId);
}
