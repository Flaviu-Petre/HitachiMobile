package com.SPRING_REST_CAPSTONE.HitachiMobile.dto;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.CustomerAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateResponse {
    private String message;
    private boolean success;
    private CustomerAddress updatedAddress;
}
