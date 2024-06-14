package com.uli.hackathon.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class PersonalInfo {

    private String name;
    private String email;
    private String phoneNo;
    private String address;
    private String bankAccountNo;
}
