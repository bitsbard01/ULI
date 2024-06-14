package com.uli.hackathon.repository.impl;

import com.uli.hackathon.entity.GoodsType;
import com.uli.hackathon.repository.GoodsTypeCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static com.uli.hackathon.utils.Constants.GOODS_TYPE;

@Slf4j
@RequiredArgsConstructor
public class GoodsTypeRepositoryImpl implements GoodsTypeCustomRepository {

    private final EntityManager entityManager;

    @Override
    public GoodsType findByGoodsType(String goodsType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GoodsType> cq = cb.createQuery(GoodsType.class);
        Root<GoodsType> root = cq.from(GoodsType.class);
        cq.select(root)
                .where(cb.equal(root.get(GOODS_TYPE), goodsType));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
