package com.uli.hackathon.repository;

import com.uli.hackathon.entity.GoodsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsTypeRepository extends JpaRepository<GoodsType, Long>,GoodsTypeCustomRepository {
    List<GoodsType> findByGoodsTypeContainingIgnoreCase(String goodsType);

}
