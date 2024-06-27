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
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Vehicle {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long vehicleId;
    private String vehicleNo;
    private String vehicleType;
    private LocalDateTime validFrom;
    private LocalDateTime validTill;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route mostFrequentRoute;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;
}