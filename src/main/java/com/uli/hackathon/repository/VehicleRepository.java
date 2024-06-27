package com.uli.hackathon.repository;

import com.uli.hackathon.entity.Vehicle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v " +
            "JOIN v.mostFrequentRoute r " +
            "WHERE SQRT(POW(r.sourceStop.locationLatitude - :sourceLat, 2) + POW(r.sourceStop.locationLongitude - :sourceLng, 2)) <= :threshold " +
            "AND SQRT(POW(r.destinationStop.locationLatitude - :destLat, 2) + POW(r.destinationStop.locationLongitude - :destLng, 2)) <= :threshold")
    List<Vehicle> findVehiclesByRouteProximity(@Param("sourceLat") double sourceLat,
                                               @Param("sourceLng") double sourceLng,
                                               @Param("destLat") double destLat,
                                               @Param("destLng") double destLng,
                                               @Param("threshold") double threshold);

    @Query("SELECT v FROM Vehicle v " +
            "JOIN v.mostFrequentRoute r " +
            "ORDER BY (SQRT(POW(r.sourceStop.locationLatitude - :sourceLat, 2) + POW(r.sourceStop.locationLongitude - :sourceLng, 2)) + " +
            "SQRT(POW(r.destinationStop.locationLatitude - :destLat, 2) + POW(r.destinationStop.locationLongitude - :destLng, 2))) ASC")
    List<Vehicle> findNearestVehicles(@Param("sourceLat") double sourceLat,
                                      @Param("sourceLng") double sourceLng,
                                      @Param("destLat") double destLat,
                                      @Param("destLng") double destLng,
                                      Pageable pageable);
}
