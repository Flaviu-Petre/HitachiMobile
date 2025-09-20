package com.SPRING_REST_CAPSTONE.HitachiMobile.repository.CustomInterface;

import com.SPRING_REST_CAPSTONE.HitachiMobile.dto.AddressUpdateRequest;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.CustomerAddress;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.List;

public interface CustomerRepositoryCustom {
    List<Customer> findCustomersInBangalore();
    List<Customer> findAllCustomers();
    CustomerAddress updateCustomerAddress(AddressUpdateRequest address);
}
