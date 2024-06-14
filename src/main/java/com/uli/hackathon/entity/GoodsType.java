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
import javax.persistence.Table;

@Entity
@Table(name = "goods_type")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GoodsType {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long goodsTypeId;

    private String goodsType;
    private String goodsDescription;
    private Double onLoadingChargePerKg;
    private Double offLoadingChargePerKg;
}