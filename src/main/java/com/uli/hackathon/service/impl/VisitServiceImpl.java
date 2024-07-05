package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Goods;
import com.uli.hackathon.entity.GoodsType;
import com.uli.hackathon.entity.Notification;
import com.uli.hackathon.entity.Order;
import com.uli.hackathon.entity.Owner;
import com.uli.hackathon.entity.Route;
import com.uli.hackathon.entity.Shipment;
import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.entity.User;
import com.uli.hackathon.entity.Vehicle;
import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.repository.VisitRepository;
import com.uli.hackathon.schemaobjects.AcceptRejectVisitSo;
import com.uli.hackathon.schemaobjects.GoodsTypeDetails;
import com.uli.hackathon.schemaobjects.OrderSearchCombinationsRequestSo;
import com.uli.hackathon.schemaobjects.TripDetailsSo;
import com.uli.hackathon.schemaobjects.VisitResponseSo;
import com.uli.hackathon.schemaobjects.VisitSequenceDetails;
import com.uli.hackathon.service.GoodsTypeService;
import com.uli.hackathon.service.NotificationService;
import com.uli.hackathon.service.OwnerService;
import com.uli.hackathon.service.ShipmentService;
import com.uli.hackathon.service.VehicleService;
import com.uli.hackathon.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.uli.hackathon.utils.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    private final StopServiceImpl stopService;

    private final RouteServiceImpl routeService;
    private final GoodsTypeService goodsTypeService;

    private final ShipmentService shipmentService;
    private final VehicleService vehicleService;

    private final OwnerService ownerService;
    private final NotificationService notificationService;
    @Override
    public void registerVisit(TripDetailsSo tripDetailsSo) {
        Stop sourceStop = stopService.getOrAddStop(tripDetailsSo.getSourceLatitude(),tripDetailsSo.getSourceLongitude()
                ,tripDetailsSo.getSourceName());
        Stop destinationStop = stopService.getOrAddStop(tripDetailsSo.getDestinationLatitude(),tripDetailsSo.getDestinationLongitude()
                ,tripDetailsSo.getDestinationName());

        Vehicle vehicle = vehicleService.getVehicle(tripDetailsSo.getVehicleId());
        if(vehicle == null){
            throw new RuntimeException("Vehicle not found with id: " + tripDetailsSo.getVehicleId());
        }

        Route route = routeService.getOrAddRoute(sourceStop,destinationStop,vehicle.getVehicleType());

        tripDetailsSo.getGoodsTypeDetailsList().forEach(goodsTypeDetails -> {
            GoodsType goodsType = goodsTypeService.getGoodsType(goodsTypeDetails.getGoodsType());
            Visit visit = Visit.builder().visitStartTime(tripDetailsSo.getVisitInitiationTime())
                    .visitEndTime(tripDetailsSo.getVisitTerminationTime()).route(route).goodsType(goodsType).vehicle(vehicle)
                    .availableVolumeCapacity(goodsTypeDetails.getVolumeCapacity())
                    .availableWeightCapacity(goodsTypeDetails.getWeightCapacity())
                    .costPerCubicMeter(goodsTypeDetails.getChargePerCubicMeter()).costPerKg(goodsTypeDetails.getChargePerKg())
                    .status(ACTIVE)
                    .build();
            visitRepository.save(visit);
        });
    }

    @Override
    public Visit addVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit getVisit(Long visitId) {
        return visitRepository.findById(visitId).orElse(null);
    }

    public VisitSequenceDetails getAllVisitIdSequences(OrderSearchCombinationsRequestSo request) {
        Stop sourceStop = stopService.getOrAddStop(request.getSourceLatitude(),request.getSourceLongitude(),request.getSourceName());
        Stop destinationStop = stopService.getOrAddStop(request.getDestinationLatitude(),request.getDestinationLongitude()
                ,request.getDestinationName());

        Map<String, List<List<Long>>> resultMap = new HashMap<>();
        request.getGoodsTypeDetailsList().forEach(goodsTypeDetails ->{
            GoodsType goodsType = goodsTypeService.getGoodsType(goodsTypeDetails.getGoodsType());

            List<Visit> visits = visitRepository.findAllByGoodsTypeAndTimeRangeAndVolumeAndWeight(
                    goodsType,
                    request.getDesiredStartTime(),
                    request.getDesiredEndTime(),
                    ACTIVE,
                    goodsTypeDetails.getVolumeCapacity(),
                    goodsTypeDetails.getWeightCapacity()
            );

            Map<Long, List<Visit>> visitMap = new HashMap<>();
            for (Visit visit : visits) {
                visitMap.computeIfAbsent(visit.getRoute().getSourceStop().getStopId(), k -> new ArrayList<>()).add(visit);
            }

            List<Long> currentPath = new ArrayList<>();
            List<List<Long>> possiblePaths = new ArrayList<>();
            findPaths(sourceStop.getStopId(), destinationStop.getStopId(), currentPath, possiblePaths, visitMap,LocalDateTime.MIN);

            if (possiblePaths.isEmpty()) {
                resultMap.put(goodsType.getGoodsType(), null);
            } else {
                resultMap.put(goodsType.getGoodsType(), possiblePaths);
            }
        });
        return VisitSequenceDetails.builder().sourceStopId(sourceStop.getStopId())
                .destinationStopId(destinationStop.getStopId()).visitIdSequences(resultMap).build();
    }

    @Override
    public Visit findVisitWithLeastEndTime(Long routeId,Long goodsTypeId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Visit> visits = visitRepository.findFirstByRouteAndVisitStartTimeAfterAndVisitEndTimeBefore(routeId,goodsTypeId,startTime,endTime, PageRequest.of(0, 1));
        return visits.isEmpty() ? null : visits.get(0);
    }

    @Override
    public Visit findVisitWithHighestStartTime(Long routeId,Long goodsTypeId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Visit> visits = visitRepository.findFirstByRouteAndVisitStartTimeAfterAndVisitEndTimeBeforeOrderByVisitStartTimeDesc(routeId,goodsTypeId,startTime,endTime,PageRequest.of(0, 1));
        return visits.isEmpty() ? null : visits.get(0);
    }

    @Override
    public String acceptRejectVisit(AcceptRejectVisitSo acceptRejectVisitSo) {
        if(StringUtils.hasLength(acceptRejectVisitSo.getStatus())){
            if(acceptRejectVisitSo.getStatus().equals(REJECTED)) {
                Visit visit = visitRepository.findById(acceptRejectVisitSo.getVisitId()).orElse(null);
                if (visit != null) {
                    visit.setStatus(REJECTED);
                    visitRepository.save(visit);
                    shipmentService.deleteShipment(visit.getVisitId());
                }
                return VISIT_REJECTED;
            } else if (acceptRejectVisitSo.getStatus().equals(ACCEPTED)) {
                Visit visit = visitRepository.findById(acceptRejectVisitSo.getVisitId()).orElse(null);
                if (visit != null) {
                    visit.setStatus(ACTIVE);
                    visit.setAvailableVolumeCapacity(acceptRejectVisitSo.getVolumeCapacity());
                    visit.setAvailableWeightCapacity(acceptRejectVisitSo.getWeightCapacity());
                    visit.setCostPerKg(acceptRejectVisitSo.getChargePerKg());
                    visit.setCostPerCubicMeter(acceptRejectVisitSo.getChargePerCubicMeter());
                    visitRepository.save(visit);
                    Shipment shipment = shipmentService.findByVisit(visit);
                    Goods goods = shipment.getGoods();
                    GoodsTypeDetails goodsTypeDetails = GoodsTypeDetails.builder()
                            .goodsType(goods.getGoodsType().getGoodsType())
                            .volumeCapacity(goods.getTotalVolume())
                            .weightCapacity(goods.getTotalWeight())
                            .build();
                    Order order = goods.getOrder();
                    Stop source = order.getSourceStop();
                    Stop destination = order.getDestinationStop();
                    OrderSearchCombinationsRequestSo requestSo = OrderSearchCombinationsRequestSo.builder().sourceLatitude(source.getLocationLatitude())
                            .sourceLongitude(source.getLocationLongitude()).sourceName(source.getStopName())
                            .destinationLatitude(destination.getLocationLatitude()).destinationLongitude(destination.getLocationLongitude())
                            .destinationName(destination.getStopName()).desiredStartTime(LocalDateTime.now().minusYears(3))
                            .desiredEndTime(LocalDateTime.now().plusYears(3)).goodsTypeDetailsList(Collections.singletonList(goodsTypeDetails)).build();
                    VisitSequenceDetails visitSequenceDetails = getAllVisitIdSequences(requestSo);
                    Map<String, List<List<Long>>> resultMap = visitSequenceDetails.getVisitIdSequences();
                    if(resultMap.get(goods.getGoodsType().getGoodsType()) != null){
                        List<Long> visitIds = shipmentService.getCancelVisitsByGoodsIdExceptVisitId(goods.getGoodsId(), visit.getVisitId());
                        for (Long id : visitIds) {
                            Visit visitNew = visitRepository.findById(id).orElse(null);
                            assert visitNew != null;
                            visitNew.setStatus(CANCELLED);
                            visitRepository.save(visitNew);
                        }
                        shipmentService.deleteShipment(goods.getGoodsId(),visit.getVisitId());
                        sendNotification(order);
                        return VISIT_ACCEPTED;
                    }else{
                        return VISIT_NOT_REQUIRED;
                    }
                }
            }
        }
        return null;
    }

    public List<VisitResponseSo> getVisitsByOwnerAndStatus(Long ownerId, String status) {
        Owner owner = ownerService.getOwner(ownerId);
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(owner);
        if (status != null && !status.isEmpty()) {
            List<Visit> visits = visitRepository.findByVehicleInAndStatus(vehicles, status);
            return mapVisitToVisitResponseSo(visits);
        } else {
            List<Visit> visits = visitRepository.findByVehicleIn(vehicles);
            return mapVisitToVisitResponseSo(visits);
        }
    }

    private List<VisitResponseSo> mapVisitToVisitResponseSo(List<Visit> visits){
        return visits.stream()
                .map(visit -> VisitResponseSo.builder()
                        .visitId(visit.getVisitId())
                        .visitInitiationTime(visit.getVisitStartTime())
                        .visitTerminationTime(visit.getVisitEndTime())
                        .availableVolumeCapacity(visit.getAvailableVolumeCapacity())
                        .availableWeightCapacity(visit.getAvailableWeightCapacity())
                        .costPerCubicMeter(visit.getCostPerCubicMeter())
                        .costPerKg(visit.getCostPerKg())
                        .status(visit.getStatus())
                        .goodsType(visit.getGoodsType().getGoodsType())
                        .sourceStop(visit.getRoute().getSourceStop().getStopName())
                        .destinationStop(visit.getRoute().getDestinationStop().getStopName())
                        .vehicle(visit.getVehicle().getVehicleNo())
                        .build())
                .collect(Collectors.toList());
    }
    private void sendNotification(Order order){
        User user = order.getConsumer().getUser();
        String orderDetails = "Requested order accepted : order id " + order.getOrderId() + " ordered from " +
                order.getSourceStop().getStopName() + " ordered to " + order.getDestinationStop().getStopName() + " booked at " +
                order.getBookingTime().toString();
        Notification notification = Notification.builder().type(ORDER_REQUEST).details(orderDetails)
                .timestamp(LocalDateTime.now()).user(user).build();
        notificationService.addNotification(notification);
    }

    private void findPaths(Long currentStopId, Long destinationStopId, List<Long> currentPath, List<List<Long>> possiblePaths,
                           Map<Long, List<Visit>> visitMap, LocalDateTime lastVisitEndTime) {

        if (currentStopId.equals(destinationStopId)) {
            possiblePaths.add(new ArrayList<>(currentPath));
            return;
        }

        if (!visitMap.containsKey(currentStopId)) {
            return;
        }

        for (Visit visit : visitMap.get(currentStopId)) {
            if (lastVisitEndTime == null || visit.getVisitStartTime().isAfter(lastVisitEndTime)) {
                currentPath.add(visit.getVisitId());
                findPaths(visit.getRoute().getDestinationStop().getStopId(), destinationStopId, currentPath, possiblePaths, visitMap, visit.getVisitEndTime());
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }
}
