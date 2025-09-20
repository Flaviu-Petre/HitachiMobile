package com.SPRING_REST_CAPSTONE.HitachiMobile.service.Interface;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimDetails;

import java.util.List;

public interface SimDetailsService {

    //Crud Operations
    SimDetails createSimDetails(SimDetails simDetails);
    SimDetails getSimDetailsById(Long id);
    SimDetails updateSimDetails(Long id, SimDetails simDetails);
    void deleteSimDetails(Long id);

    SimDetails assignSimToCustomer(Long simId, Long customerId);
    List<SimDetails> getActiveSimDetails();
    String fetchSimStatus(String simNumber, String serviceNumber);


}
