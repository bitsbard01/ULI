package com.uli.hackathon.service.impl;

import com.uli.hackathon.entity.GoodsType;
import com.uli.hackathon.repository.GoodsTypeRepository;
import com.uli.hackathon.service.GoodsTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsTypeServiceImpl implements GoodsTypeService {

    private final GoodsTypeRepository goodsTypeRepository;

    @Override
    public GoodsType getGoodsType(String goodsType) {
        return goodsTypeRepository.findByGoodsType(goodsType);
    }

    public List<String> findGoodsTypeByKeyword(String keyword) {
        List<GoodsType> goodsTypes = goodsTypeRepository.findByGoodsTypeContainingIgnoreCase(keyword);
        return goodsTypes.stream()
                .map(GoodsType::getGoodsType)
                .collect(Collectors.toList());
    }
}
