package com.uli.hackathon.service;

import com.uli.hackathon.entity.Order;
import com.uli.hackathon.schemaobjects.OrderPlaceRequestSo;
import com.uli.hackathon.schemaobjects.OrderSearchCombinationsRequestSo;
import com.uli.hackathon.schemaobjects.OrderSearchCombinationsResponseSo;
import com.uli.hackathon.schemaobjects.RequestOrderSo;

import java.util.List;

public interface OrderService {

    void placeOrder(OrderPlaceRequestSo orderPlaceRequestSo);

    OrderSearchCombinationsResponseSo searchJourneyCombinations(OrderSearchCombinationsRequestSo
                                                                              orderSearchCombinationsRequestSo);

    void requestOrder(RequestOrderSo requestOrderSo);

    Double getCost(Long orderId);
}
