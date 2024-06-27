package com.uli.hackathon.service;

import com.uli.hackathon.entity.Goods;
import com.uli.hackathon.entity.Order;

import java.util.List;

public interface GoodsService {

    Goods addGoods(Goods goods);

    Goods getGoods(Order order);
}
