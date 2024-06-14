package com.uli.hackathon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "goods")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Goods {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long goodsId;

    private Long totalVolume;
    private Long totalWeight;

    @ManyToOne
    @JoinColumn(name = "goods_type_id", nullable = false)
    private GoodsType goodsType;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}