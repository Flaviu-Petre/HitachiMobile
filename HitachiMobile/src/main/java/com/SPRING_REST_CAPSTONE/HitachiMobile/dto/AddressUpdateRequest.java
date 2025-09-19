package com.SPRING_REST_CAPSTONE.HitachiMobile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateRequest {
    private Long customerId;
    private String address;
    private String city;
    private String state;
    private String zipCode;
}
