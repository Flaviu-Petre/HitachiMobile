package com.SPRING_REST_CAPSTONE.HitachiMobile.service;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimDetails;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.InvalidDetailsException;
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
}
