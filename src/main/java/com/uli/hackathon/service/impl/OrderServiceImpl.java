package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Consumer;
import com.uli.hackathon.entity.Goods;
import com.uli.hackathon.entity.GoodsType;
import com.uli.hackathon.entity.Order;
import com.uli.hackathon.entity.Shipment;
import com.uli.hackathon.entity.Stop;
import com.uli.hackathon.entity.Visit;
import com.uli.hackathon.repository.OrderRepository;
import com.uli.hackathon.schemaobjects.OrderPlaceRequestSo;
import com.uli.hackathon.service.ConsumerService;
import com.uli.hackathon.service.GoodsService;
import com.uli.hackathon.service.GoodsTypeService;
import com.uli.hackathon.service.OrderService;
import com.uli.hackathon.service.ShipmentService;
import com.uli.hackathon.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public void placeOrder(OrderPlaceRequestSo orderPlaceRequestSo) {
        Consumer consumer = consumerService.getConsumer(orderPlaceRequestSo.getConsumerId());
        if (consumer == null){
            throw new RuntimeException("Consumer not found with id: " + orderPlaceRequestSo.getConsumerId());
        }

        Stop sourceStop = stopService.getStop(orderPlaceRequestSo.getSourceLatitude(),orderPlaceRequestSo.getSourceLongitude());
        if(sourceStop == null){
            Stop stop = Stop.builder().stopName(orderPlaceRequestSo.getSourceName())
                    .locationLatitude(orderPlaceRequestSo.getSourceLatitude()).locationLongitude(orderPlaceRequestSo.getSourceLongitude())
                    .build();
            sourceStop = stopService.addStop(stop);
        }
        Stop destinationStop = stopService.getStop(orderPlaceRequestSo.getDestinationLatitude(),orderPlaceRequestSo.getDestinationLongitude());
        if(destinationStop == null){
            Stop stop = Stop.builder().stopName(orderPlaceRequestSo.getDestinationName())
                    .locationLatitude(orderPlaceRequestSo.getDestinationLatitude()).locationLongitude(orderPlaceRequestSo.getDestinationLongitude())
                    .build();
            destinationStop = stopService.addStop(stop);
        }

        Order order = Order.builder().bookingTime(LocalDateTime.now()).consumer(consumer)
                .sourceStop(sourceStop).destinationStop(destinationStop).cost(orderPlaceRequestSo.getCost()).build();

        orderRepository.save(order);

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
                Shipment shipment = Shipment.builder().visit(visit).goods(goods).build();
                shipmentService.addShipment(shipment);
            });
        });
    }
}
