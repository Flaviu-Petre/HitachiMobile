package com.SPRING_REST_CAPSTONE.HitachiMobile.service.Interface;

import com.SPRING_REST_CAPSTONE.HitachiMobile.dto.AddressUpdateRequest;
import com.SPRING_REST_CAPSTONE.HitachiMobile.dto.IdProofValidationRequest;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.CustomerAddress;

import java.util.List;

public interface CustomerService {

    //Crud Operations
    Customer createCustomer(Customer customer);
    Customer getCustomerById(Long id);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);

    String validateSimAndGetOffers(String simNumber, String serviceNumber);
    String validateCustomerBasicDetails(String emailAddress, String dateOfBirth);
    CustomerAddress updateCustomerAddress(AddressUpdateRequest request);
    String validateCustomerIdProof(IdProofValidationRequest request);

    List<Customer> getCustomersInBangalore();


}
