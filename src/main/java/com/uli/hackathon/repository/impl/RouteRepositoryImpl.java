package com.uli.hackathon.repository.impl;

import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.repository.RouteCustomRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;

import static com.uli.hackathon.utils.Constants.*;

@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteCustomRepository {

    private final EntityManager entityManager;

    @Override
    public Route getRoute(Stop sourceStop, Stop destinationStop, String routeCategory) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Route> cq = cb.createQuery(Route.class);
        Root<Route> root = cq.from(Route.class);

        Predicate sourcePredicate = cb.equal(root.get(SOURCE_STOP), sourceStop);
        Predicate destinationPredicate = cb.equal(root.get(DESTINATION_STOP), destinationStop);
        Predicate categoryPredicate = cb.equal(root.get(ROUTE_TYPE), routeCategory);

        cq.select(root)
                .where(cb.and(sourcePredicate, destinationPredicate, categoryPredicate));

        List<Route> results = entityManager.createQuery(cq).getResultList();

        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }
}
