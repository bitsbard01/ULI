package com.uli.hackathon.schemaobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoRequestSo {

    private String userName;
    private String userEmail;
    private String userPhoneNo;
    private String userAddress;
    private String userBankAccountNo;
}
