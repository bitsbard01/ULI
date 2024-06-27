package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.Goods;
import com.uli.hackathon.entity.Order;
import com.uli.hackathon.repository.GoodsRepository;
import com.uli.hackathon.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;

    @Override
    public Goods addGoods(Goods goods) {
        return goodsRepository.save(goods);
    }

    @Override
    public Goods getGoods(Order order) {
        return goodsRepository.findByOrder(order);
    }
}
