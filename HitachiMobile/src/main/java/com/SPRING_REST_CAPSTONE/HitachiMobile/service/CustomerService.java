package com.SPRING_REST_CAPSTONE.HitachiMobile.service;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;

public interface CustomerService {

    //Crud Operations
    Customer createCustomer(Customer customer);
    Customer getCustomerById(Long id);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);


    String validateSimAndGetOffers(String simNumber, String serviceNumber);
}
