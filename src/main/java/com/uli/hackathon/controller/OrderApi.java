package com.uli.hackathon.controller;

import com.uli.hackathon.schemaobjects.OrderPlaceRequestSo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/order")
public interface OrderApi {

    @PostMapping(value = "/place")
    void placeOrder(@RequestBody OrderPlaceRequestSo orderPlaceRequestSo);
}
