package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Consumer;
import com.uli.hackathon.entity.Goods;
import com.uli.hackathon.entity.GoodsType;
import com.uli.hackathon.entity.Notification;
import com.uli.hackathon.entity.Order;
import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Shipment;
import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.entity.User;
import com.uli.hackathon.entity.Vehicle;
import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.helper.CommonHelper;
import com.uli.hackathon.repository.OrderRepository;
import com.uli.hackathon.schemaobjects.GoodsTypeDetails;
import com.uli.hackathon.schemaobjects.JourneyCostDetailsSo;
import com.uli.hackathon.schemaobjects.JourneyDetailsSo;
import com.uli.hackathon.schemaobjects.OrderDetailsSo;
import com.uli.hackathon.schemaobjects.OrderPlaceRequestSo;
import com.uli.hackathon.schemaobjects.OrderResponseSo;
import com.uli.hackathon.schemaobjects.OrderSearchCombinationsRequestSo;
import com.uli.hackathon.schemaobjects.OrderSearchCombinationsResponseSo;
import com.uli.hackathon.schemaobjects.ReachDetails;
import com.uli.hackathon.schemaobjects.RequestOrderSo;
import com.uli.hackathon.schemaobjects.VisitSequenceDetails;
import com.uli.hackathon.service.ConsumerService;
import com.uli.hackathon.service.GoodsService;
import com.uli.hackathon.service.GoodsTypeService;
import com.uli.hackathon.service.NotificationService;
import com.uli.hackathon.service.OrderService;
import com.uli.hackathon.service.RouteService;
import com.uli.hackathon.service.ShipmentService;
import com.uli.hackathon.service.VehicleService;
import com.uli.hackathon.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.uli.hackathon.utils.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ConsumerService consumerService;

    private final StopServiceImpl stopService;

    private final GoodsTypeService goodsTypeService;

    private final GoodsService goodsService;

    private final VisitService visitService;

    private final ShipmentService shipmentService;

    private final VehicleService vehicleService;

    private final RouteService routeService;

    private final NotificationService notificationService;

    private final CommonHelper commonHelper;
    @Override
    public void placeOrder(OrderPlaceRequestSo orderPlaceRequestSo) {
        Order order;
        if(orderPlaceRequestSo.getOrderId() != null){
            order = orderRepository.findById(orderPlaceRequestSo.getOrderId()).orElse(null);
            assert order != null;
            order.setCost(orderPlaceRequestSo.getCost());
            order.setStatus(PLACED);
            orderRepository.save(order);
        }else{
            Consumer consumer = consumerService.getConsumer(orderPlaceRequestSo.getConsumerId());
            if (consumer == null){
                throw new RuntimeException("Consumer not found with id: " + orderPlaceRequestSo.getConsumerId());
            }

            Stop sourceStop = stopService.getStop(orderPlaceRequestSo.getSourceStopId());
            Stop destinationStop = stopService.getStop(orderPlaceRequestSo.getDestinationStopId());

            order = Order.builder().bookingTime(LocalDateTime.now()).consumer(consumer)
                    .sourceStop(sourceStop).destinationStop(destinationStop).cost(orderPlaceRequestSo.getCost()).status(PLACED).build();

            orderRepository.save(order);
        }

        orderPlaceRequestSo.getOrderDetailsSoList().forEach(orderDetailsSo -> {
            GoodsType goodsType = goodsTypeService.getGoodsType(orderDetailsSo.getGoodsType());
            Goods goods = Goods.builder().order(order).goodsType(goodsType).totalVolume(orderDetailsSo.getVolumeCapacity())
                    .totalWeight(orderDetailsSo.getWeightCapacity()).build();
            goodsService.addGoods(goods);
            orderDetailsSo.getVisits().forEach(visitId->{
                Visit visit = visitService.getVisit(visitId);
                if (visit == null){
                    throw new RuntimeException("Visit not found with id: " + visitId);
                }
                visit.setAvailableVolumeCapacity(visit.getAvailableVolumeCapacity() - goods.getTotalVolume());
                visit.setAvailableWeightCapacity(visit.getAvailableWeightCapacity() - goods.getTotalWeight());
                visitService.addVisit(visit);
                Shipment shipment = Shipment.builder().visit(visit).goods(goods).build();
                shipmentService.addShipment(shipment);
            });
        });
    }

    @Override
    public OrderSearchCombinationsResponseSo searchJourneyCombinations(
            OrderSearchCombinationsRequestSo orderSearchCombinationsRequestSo) {
        String orderAvailabilityStatus = NOT_AVAILABLE;
        Map<String, GoodsTypeDetails> goodsTypeDetailsMap = orderSearchCombinationsRequestSo.getGoodsTypeDetailsList().stream()
                .collect(Collectors.toMap(GoodsTypeDetails::getGoodsType, gtd -> gtd));

        VisitSequenceDetails visitSequenceDetails = visitService.getAllVisitIdSequences(orderSearchCombinationsRequestSo);
        Map<String, List<List<Long>>> goodsTypeVisitIdSequencesMap = visitSequenceDetails.getVisitIdSequences();

        Map<String, List<JourneyCostDetailsSo>> goodsTypeJourneyListMap = new HashMap<>();

        for (Map.Entry<String, List<List<Long>>> entry : goodsTypeVisitIdSequencesMap.entrySet()) {
            String goodsType = entry.getKey();
            List<List<Long>> visitIdSequences = entry.getValue();

            if (visitIdSequences == null) {
                goodsTypeJourneyListMap.put(goodsType, null);
                if(orderAvailabilityStatus.equals(AVAILABLE)){
                    orderAvailabilityStatus = PARTIALLY_AVAILABLE;
                }
                continue;
            }

            GoodsTypeDetails goodsTypeDetails = goodsTypeDetailsMap.get(goodsType);
            GoodsType goodsType1 = goodsTypeService.getGoodsType(goodsType);
            List<JourneyCostDetailsSo> journeyDetailsListSequences = new ArrayList<>();
            for (List<Long> visitIdSequence : visitIdSequences) {
                List<JourneyDetailsSo> journeyDetailsList = new ArrayList<>();
                BigDecimal totalCost = BigDecimal.ZERO;

                for (Long visitId : visitIdSequence) {
                    Visit visit = visitService.getVisit(visitId);
                    double cost = calculateCost(visit, goodsTypeDetails, goodsType1);

                    JourneyDetailsSo journeyDetails = JourneyDetailsSo.builder()
                            .visitId(visit.getVisitId())
                            .visitInitiationTime(visit.getVisitStartTime())
                            .visitTerminationTime(visit.getVisitEndTime())
                            .vehicleId(visit.getVehicle().getVehicleId())
                            .vehicleCategory(visit.getVehicle().getVehicleType())
                            .cost(cost)
                            .sourceLatitude(visit.getRoute().getSourceStop().getLocationLatitude())
                            .sourceLongitude(visit.getRoute().getSourceStop().getLocationLongitude())
                            .sourceName(visit.getRoute().getSourceStop().getStopName())
                            .destinationLatitude(visit.getRoute().getDestinationStop().getLocationLatitude())
                            .destinationLongitude(visit.getRoute().getDestinationStop().getLocationLongitude())
                            .destinationName(visit.getRoute().getDestinationStop().getStopName())
                            .build();
                    totalCost = totalCost.add(BigDecimal.valueOf(cost));
                    journeyDetailsList.add(journeyDetails);
                }

                JourneyCostDetailsSo journeyCostDetailsSo = JourneyCostDetailsSo.builder()
                        .journeyDetailsSoList(journeyDetailsList)
                        .cost(totalCost.doubleValue())
                        .build();

                journeyDetailsListSequences.add(journeyCostDetailsSo);
            }
            goodsTypeJourneyListMap.put(goodsType, journeyDetailsListSequences);
            if (orderAvailabilityStatus.equals(NOT_AVAILABLE)){
                orderAvailabilityStatus = AVAILABLE;
            }
        }

        return OrderSearchCombinationsResponseSo.builder().goodsTypeJourneyListMap(goodsTypeJourneyListMap)
                .orderAvailabilityStatus(orderAvailabilityStatus).sourceStopId(visitSequenceDetails.getSourceStopId())
                .destinationStopId(visitSequenceDetails.getDestinationStopId()).build();
    }

    @Override
    public void requestOrder(RequestOrderSo requestOrderSo) {
        requestOrderSo.getGoodsTypeDetailsList().forEach(goodsTypeDetails -> {
            Consumer consumer = consumerService.getConsumer(requestOrderSo.getConsumerId());
            if (consumer == null){
                throw new RuntimeException("Consumer not found with id: " + requestOrderSo.getConsumerId());
            }

            Stop sourceStop = stopService.getStop(requestOrderSo.getSourceStopId());
            Stop destinationStop = stopService.getStop(requestOrderSo.getDestinationStopId());

            Order order = Order.builder().bookingTime(LocalDateTime.now()).consumer(consumer)
                    .sourceStop(sourceStop).destinationStop(destinationStop).status(REQUESTED).build();

            Order orderSaved =orderRepository.save(order);

            GoodsType goodsType = goodsTypeService.getGoodsType(goodsTypeDetails.getGoodsType());
            Goods goods = Goods.builder().order(orderSaved).goodsType(goodsType).totalVolume(goodsTypeDetails.getVolumeCapacity())
                    .totalWeight(goodsTypeDetails.getWeightCapacity()).build();
            goodsService.addGoods(goods);
            notifyVehicles(requestOrderSo,goodsTypeDetails,goods);
        });
    }

    @Override
    public OrderPlaceRequestSo getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        Goods goods = goodsService.getGoods(order);
        List<Visit> visits = shipmentService.getVisits(goods);
        GoodsTypeDetails goodsTypeDetails = GoodsTypeDetails.builder().weightCapacity(goods.getTotalWeight())
                .volumeCapacity(goods.getTotalVolume()).build();
        GoodsType goodsType = goods.getGoodsType();
        List<Long> visitIds = visits.stream()
                .map(Visit::getVisitId)
                .collect(Collectors.toList());
        Double cost = visits.stream()
                .mapToDouble(visit -> calculateCost(visit, goodsTypeDetails, goodsType))
                .sum();
        OrderDetailsSo orderDetailsSo = OrderDetailsSo.builder().visits(visitIds).goodsType(goodsType.getGoodsType())
                .volumeCapacity(goods.getTotalVolume()).weightCapacity(goods.getTotalWeight()).build();
        OrderPlaceRequestSo orderPlaceRequestSo = OrderPlaceRequestSo.builder().orderId(orderId)
                .consumerId(order.getConsumer().getConsumerId()).orderDetailsSoList(Collections.singletonList(orderDetailsSo))
                .cost(cost).sourceStopId(order.getSourceStop().getStopId())
                .destinationStopId(order.getDestinationStop().getStopId()).build();
        return orderPlaceRequestSo;
    }

    public List<OrderResponseSo> getOrdersByConsumerAndStatus(Long consumerId, String status) {
        if (status != null && !status.isEmpty()) {
            List<Order> orders = orderRepository.findByConsumerConsumerIdAndStatus(consumerId, status);
            return orders.stream()
                    .map(order -> OrderResponseSo.builder()
                            .orderId(order.getOrderId())
                            .bookingTime(order.getBookingTime())
                            .status(order.getStatus())
                            .cost(order.getCost())
                            .sourceStop(order.getSourceStop().getStopName())
                            .destinationStop(order.getDestinationStop().getStopName())
                            .build())
                    .collect(Collectors.toList());
        } else {
            List<Order> orders =orderRepository.findByConsumerConsumerId(consumerId);
            return orders.stream()
                    .map(order -> OrderResponseSo.builder()
                            .orderId(order.getOrderId())
                            .bookingTime(order.getBookingTime())
                            .status(order.getStatus())
                            .cost(order.getCost())
                            .sourceStop(order.getSourceStop().getStopName())
                            .destinationStop(order.getDestinationStop().getStopName())
                            .build())
                    .collect(Collectors.toList());
        }
    }
    private double calculateCost(Visit visit, GoodsTypeDetails goodsTypeDetails, GoodsType goodsType) {
        double volumeCost = visit.getCostPerCubicMeter() * goodsTypeDetails.getVolumeCapacity();
        double weightCost = visit.getCostPerKg() * goodsTypeDetails.getWeightCapacity();
        double onLoadingCharge = goodsType.getOnLoadingChargePerKg() * goodsTypeDetails.getWeightCapacity();
        double offLoadingCharge = goodsType.getOffLoadingChargePerKg() * goodsTypeDetails.getWeightCapacity();
        double val = Math.max(volumeCost, weightCost) + onLoadingCharge + offLoadingCharge;
        return val;
    }

    private void notifyVehicles(RequestOrderSo request, GoodsTypeDetails goodsTypeDetails,Goods goods){
        List<Visit> potentialVisits = getPotentialVisits(request,goods);
        potentialVisits.forEach(visit -> {
            List<Vehicle> vehicles =vehicleService.findVehiclesByRoute(visit.getRoute());
            vehicles.forEach(vehicle -> {
                Visit newVisit = Visit.builder().build();
                BeanUtils.copyProperties(visit,newVisit);
                Route route = routeService.getOrAddRoute(visit.getRoute().getSourceStop(),visit.getRoute().getDestinationStop(),vehicle.getVehicleType());
                newVisit.setGoodsType(goods.getGoodsType());
                newVisit.setAvailableVolumeCapacity(goodsTypeDetails.getVolumeCapacity());
                newVisit.setAvailableWeightCapacity(goodsTypeDetails.getWeightCapacity());
                newVisit.setVehicle(vehicle);
                newVisit.setStatus(REQUESTED);
                newVisit.setRoute(route);
                newVisit = visitService.addVisit(newVisit);
                sendNotification(newVisit);
                Shipment shipment = Shipment.builder().visit(newVisit).goods(goods).build();
                shipmentService.addShipment(shipment);
            });
        });
    }

    private List<Visit> getPotentialVisits(RequestOrderSo request,Goods goods) {
        Stop sourceStop = stopService.getStop(request.getSourceStopId());
        Stop destinationStop = stopService.getStop(request.getDestinationStopId());
        Set<ReachDetails> reachableFromSource = findReachableStopsFromSource(sourceStop.getStopId(),request.getStartTime(),request.getEndTime(),goods.getGoodsType().getGoodsTypeId());
        Set<ReachDetails> reachableToDestination = findReachableStopsToDestination(destinationStop.getStopId(),request.getStartTime(),request.getEndTime(),goods.getGoodsType().getGoodsTypeId());

        List<Visit> allVisits = new ArrayList<>();
        for (ReachDetails from : reachableFromSource) {
            for (ReachDetails to : reachableToDestination) {
                if (!from.equals(to) && to.getReachedTime().isAfter(from.getReachedTime())) {
                    double distance = commonHelper.calculateDistance(
                            stopService.getStop(from.getStopId()).getLocationLatitude(), stopService.getStop(from.getStopId()).getLocationLongitude(),
                            stopService.getStop(to.getStopId()).getLocationLatitude(), stopService.getStop(to.getStopId()).getLocationLongitude()
                    );
                    Route route = Route.builder().build();
                    route.setSourceStop(stopService.getStop(from.getStopId()));
                    route.setDestinationStop(stopService.getStop(to.getStopId()));
                    route.setDistance(distance);
                    Visit visit = Visit.builder().route(route).visitStartTime(from.getReachedTime()).visitEndTime(to.getReachedTime())
                            .build();
                    allVisits.add(visit);
                }
            }
        }

        return allVisits.stream()
                .sorted(Comparator.comparingDouble(v -> v.getRoute().getDistance()))
                .limit(3)
                .collect(Collectors.toList());
    }

    private Set<ReachDetails> findReachableStopsFromSource(Long sourceStopId,LocalDateTime startTime, LocalDateTime endTime, Long goodsTypeId) {
        Set<ReachDetails> reachableStops = new HashSet<>();
        Set<Long> visited = new HashSet<>();
        dfsFromSource(sourceStopId, visited, reachableStops,startTime,endTime,goodsTypeId);
        return reachableStops;
    }

    private void dfsFromSource(Long currentStopId, Set<Long> visited, Set<ReachDetails> reachableStops,LocalDateTime startTime, LocalDateTime endTime,Long goodsTypeId) {
        visited.add(currentStopId);
        reachableStops.add(ReachDetails.builder().stopId(currentStopId).reachedTime(startTime).build());

        List<Route> routes = routeService.getRouteBySourceId(currentStopId);
        for (Route route : routes) {
            Visit visit = visitService.findVisitWithLeastEndTime(route.getRouteId(),goodsTypeId,startTime,endTime);
            if(visit == null){
                continue;
            }
            Long nextStopId = route.getDestinationStop().getStopId();
            if (!visited.contains(nextStopId)) {
                dfsFromSource(nextStopId, visited, reachableStops,visit.getVisitEndTime(),endTime,goodsTypeId);
            }
        }
    }

    private Set<ReachDetails> findReachableStopsToDestination(Long destinationStopId,LocalDateTime startTime, LocalDateTime endTime,Long goodsTypeId) {
        Set<ReachDetails> reachableStops = new HashSet<>();
        Set<Long> visited = new HashSet<>();
        dfsToDestination(destinationStopId, visited, reachableStops,startTime,endTime,goodsTypeId);
        return reachableStops;
    }

    private void dfsToDestination(Long currentStopId, Set<Long> visited, Set<ReachDetails> reachableStops,LocalDateTime startTime, LocalDateTime endTime,Long goodsTypeId) {
        visited.add(currentStopId);
        reachableStops.add(ReachDetails.builder().stopId(currentStopId).reachedTime(endTime).build());

        List<Route> routes = routeService.getRouteByDestinationId(currentStopId);
        for (Route route : routes) {
            Visit visit = visitService.findVisitWithHighestStartTime(route.getRouteId(),goodsTypeId,startTime,endTime);
            if(visit == null){
                continue;
            }
            Long previousStopId = route.getSourceStop().getStopId();
            if (!visited.contains(previousStopId)) {
                dfsToDestination(previousStopId, visited, reachableStops,startTime,visit.getVisitStartTime(),goodsTypeId);
            }
        }
    }

    private void sendNotification(Visit visit){
        Vehicle vehicle = visit.getVehicle();
        User user = vehicleService.getVehicle(vehicle.getVehicleId()).getOwner().getUser();
        String visitDetails = "Visit Requested :" + " visit required between " + visit.getVisitStartTime().toString() + " - "
                + visit.getVisitEndTime().toString() + " from " + visit.getRoute().getSourceStop().getStopName() + " to " +
                visit.getRoute().getDestinationStop().getStopName() + " for goods type " + visit.getGoodsType().getGoodsType()
                + " and vehicle " + vehicle.getVehicleNo()
                + " with minimum volume capacity required " + visit.getAvailableVolumeCapacity() +
                " and minimum weight capacity required " + visit.getAvailableWeightCapacity();
        Notification notification = Notification.builder().user(user).type(VISIT_REQUEST).details(visitDetails)
                .status(REQUESTED).timestamp(LocalDateTime.now()).build();
        notificationService.addNotification(notification);
    }
}
