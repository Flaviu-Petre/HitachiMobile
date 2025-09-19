package com.SPRING_REST_CAPSTONE.HitachiMobile.service;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;

public interface CustomerService {

    String validateSimAndGetOffers(String simNumber, String serviceNumber);
}
