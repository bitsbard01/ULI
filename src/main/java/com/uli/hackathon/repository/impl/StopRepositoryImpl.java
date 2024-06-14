package com.uli.hackathon.repository.impl;

import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.repository.StopCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;

import static com.uli.hackathon.utils.Constants.LOCATION_LATITUDE;
import static com.uli.hackathon.utils.Constants.LOCATION_LONGITUDE;

@RequiredArgsConstructor
@Slf4j
public class StopRepositoryImpl implements StopCustomRepository {

    private final EntityManager entityManager;

    @Override
    public Stop getStop(String locationLatitude, String locationLongitude) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stop> cq = cb.createQuery(Stop.class);
        Root<Stop> root = cq.from(Stop.class);

        Predicate latitudePredicate = cb.equal(root.get(LOCATION_LATITUDE), locationLatitude);
        Predicate longitudePredicate = cb.equal(root.get(LOCATION_LONGITUDE), locationLongitude);

        cq.select(root)
                .where(cb.and(latitudePredicate, longitudePredicate));

        List<Stop> results = entityManager.createQuery(cq).getResultList();

        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }
}
