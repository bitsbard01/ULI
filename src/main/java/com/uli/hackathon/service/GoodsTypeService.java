package com.uli.hackathon.service;

import com.uli.hackathon.entity.GoodsType;

import java.util.List;

public interface GoodsTypeService {

    GoodsType getGoodsType(String goodsType);

    List<String> findGoodsTypeByKeyword(String keyword);
}
