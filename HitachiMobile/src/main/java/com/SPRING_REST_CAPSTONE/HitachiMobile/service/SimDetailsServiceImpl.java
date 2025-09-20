package com.SPRING_REST_CAPSTONE.HitachiMobile.service;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimDetails;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.InvalidDetailsException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.Interface.CustomerRepository;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.Interface.SimDetailsRepository;
import com.SPRING_REST_CAPSTONE.HitachiMobile.service.Interface.SimDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SimDetailsServiceImpl implements SimDetailsService {

    @Autowired
    private SimDetailsRepository simDetailsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public SimDetails createSimDetails(SimDetails simDetails) {
        return simDetailsRepository.save(simDetails);
    }

    @Override
    public SimDetails getSimDetailsById(Long id) {
        return simDetailsRepository.findById(id)
                .orElseThrow(() -> new InvalidDetailsException("SimDetails not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public SimDetails updateSimDetails(Long id, SimDetails simDetails) {
        SimDetails updatedDetails = getSimDetailsById(id);

        updatedDetails.setSimNumber(simDetails.getSimNumber());
        updatedDetails.setServiceNumber(simDetails.getServiceNumber());
        updatedDetails.setSimStatus(simDetails.getSimStatus());

        return simDetailsRepository.save(updatedDetails);
    }

    @Override
    public void deleteSimDetails(Long id) {
        simDetailsRepository.deleteById(id);
    }

    @Override
    public SimDetails assignSimToCustomer(Long simId, Long customerId) {
        SimDetails simDetails = getSimDetailsById(simId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new InvalidDetailsException("Customer not found"));

        simDetails.setCustomer(customer);
        return simDetailsRepository.save(simDetails);
    }
}
