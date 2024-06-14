package com.uli.hackathon.service;

import com.uli.hackathon.schemaobjects.OrderPlaceRequestSo;

public interface OrderService {

    void placeOrder(OrderPlaceRequestSo orderPlaceRequestSo);
}
