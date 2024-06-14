package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.GoodsType;
import com.uli.hackathon.repository.GoodsTypeRepository;
import com.uli.hackathon.service.GoodsTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsTypeServiceImpl implements GoodsTypeService {

    private final GoodsTypeRepository goodsTypeRepository;

    @Override
    public GoodsType getGoodsType(String goodsType) {
        return goodsTypeRepository.findByGoodsType(goodsType);
    }
}
