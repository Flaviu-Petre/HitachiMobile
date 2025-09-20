package com.SPRING_REST_CAPSTONE.HitachiMobile.repository.CustomInterface;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;

import java.util.List;

public interface CustomerRepositoryCustom {
    List<Customer> findCustomersInBangalore();
}
