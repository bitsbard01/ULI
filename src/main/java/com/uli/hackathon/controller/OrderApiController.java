package com.uli.hackathon.controller;

import com.uli.hackathon.schemaobjects.OrderPlaceRequestSo;
import com.uli.hackathon.schemaobjects.OrderSearchCombinationsRequestSo;
import com.uli.hackathon.schemaobjects.OrderSearchCombinationsResponseSo;
import com.uli.hackathon.schemaobjects.RequestOrderSo;
import com.uli.hackathon.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderApiController implements OrderApi{

    private final OrderService orderService;

    @Override
    public void placeOrder(OrderPlaceRequestSo orderPlaceRequestSo) {
        orderService.placeOrder(orderPlaceRequestSo);
    }

    @Override
    public OrderSearchCombinationsResponseSo searchJourneyCombinations(
            OrderSearchCombinationsRequestSo orderSearchCombinationsRequestSo) {
        return orderService.searchJourneyCombinations(orderSearchCombinationsRequestSo);
    }

    @Override
    public void requestOrder(RequestOrderSo requestOrderSo) {
        orderService.requestOrder(requestOrderSo);
    }

    @Override
    public Double getCost(Long id) {
        return orderService.getCost(id);
    }
}
