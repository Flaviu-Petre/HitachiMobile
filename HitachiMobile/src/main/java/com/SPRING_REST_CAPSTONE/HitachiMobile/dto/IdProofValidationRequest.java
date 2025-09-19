package com.SPRING_REST_CAPSTONE.HitachiMobile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdProofValidationRequest {
    private String aadharNumber;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
}
