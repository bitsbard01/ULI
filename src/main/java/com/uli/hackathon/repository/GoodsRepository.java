package com.uli.hackathon.repository;

import com.uli.hackathon.entity.Goods;
import com.uli.hackathon.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Goods findByOrder(Order order);
}
