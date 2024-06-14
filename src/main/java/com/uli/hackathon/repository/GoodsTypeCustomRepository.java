package com.uli.hackathon.repository;

import com.uli.hackathon.entity.GoodsType;

public interface GoodsTypeCustomRepository {

    GoodsType findByGoodsType(String goodsType);
}
