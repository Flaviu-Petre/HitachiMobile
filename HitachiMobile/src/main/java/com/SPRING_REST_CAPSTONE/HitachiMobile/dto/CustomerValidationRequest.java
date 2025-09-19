package com.SPRING_REST_CAPSTONE.HitachiMobile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerValidationRequest {

    private String emailAddress;
    private String dateOfBirth;
}
