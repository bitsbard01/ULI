package com.uli.hackathon.controller;

import com.uli.hackathon.entity.GoodsType;
import com.uli.hackathon.service.GoodsTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GoodsTypeApiController implements GoodsTypeApi{

    private final GoodsTypeService goodsTypeService;

    @Override
    public List<String> getGoodsType(String type) {
        return goodsTypeService.findGoodsTypeByKeyword(type);
    }
}
