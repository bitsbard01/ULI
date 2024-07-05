package com.uli.hackathon.controller;

import com.uli.hackathon.entity.GoodsType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/goods-type")
public interface GoodsTypeApi {

    @GetMapping(value = "/autocomplete/{type}")
    List<String> getGoodsType(@PathVariable String type);
}
