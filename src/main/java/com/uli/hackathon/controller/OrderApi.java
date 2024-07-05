package com.uli.hackathon.controller;

import com.uli.hackathon.schemaobjects.OrderPlaceRequestSo;
import com.uli.hackathon.schemaobjects.OrderResponseSo;
import com.uli.hackathon.schemaobjects.OrderSearchCombinationsRequestSo;
import com.uli.hackathon.schemaobjects.OrderSearchCombinationsResponseSo;
import com.uli.hackathon.schemaobjects.RequestOrderSo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RequestMapping("/order")
public interface OrderApi {

    @PostMapping(value = "/place")
    void placeOrder(@RequestBody OrderPlaceRequestSo orderPlaceRequestSo);

    @PostMapping(value = "/search-combinations")
    OrderSearchCombinationsResponseSo searchJourneyCombinations(@RequestBody OrderSearchCombinationsRequestSo
                                                                              orderSearchCombinationsRequestSo);

    @PostMapping(value = "/request")
    void requestOrder(@RequestBody RequestOrderSo requestOrderSo);

    @GetMapping(value = "/order-details/{id}")
    OrderPlaceRequestSo getOrderDetails(@PathVariable Long id);

    @GetMapping(value = "/orders/{consumerId}")
    List<OrderResponseSo> getOrders(@PathVariable Long consumerId, @RequestParam(required = false) String status);
}
