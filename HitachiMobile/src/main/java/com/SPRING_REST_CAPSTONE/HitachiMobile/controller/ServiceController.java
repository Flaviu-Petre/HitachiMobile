package com.SPRING_REST_CAPSTONE.HitachiMobile.controller;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimDetails;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.InvalidDetailsException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.Interface.SimDetailsRepository;
import com.SPRING_REST_CAPSTONE.HitachiMobile.service.Interface.SimDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simDetails")
@AllArgsConstructor
public class ServiceController {

    @Autowired
    private SimDetailsService simDetailsService;

    //region Crud Operations
    @PostMapping("/createSim")
    public SimDetails createSimDetails(SimDetails simDetails) {
        return simDetailsService.createSimDetails(simDetails);
    }

    @GetMapping("getSimById/{id}")
    public ResponseEntity<SimDetails> getCustomerById(@PathVariable Long id) {
        try {
            SimDetails simDetails = simDetailsService.getSimDetailsById(id);
            return ResponseEntity.ok(simDetails);
        } catch (InvalidDetailsException e) {
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

    @PutMapping("/updateSim/{id}")
    public ResponseEntity<String> updateSimDetails(@PathVariable Long id, @RequestBody SimDetails simDetails) {
        try {
            simDetailsService.updateSimDetails(id, simDetails);
            return ResponseEntity.ok("SimDetails updated successfully.");
        } catch (InvalidDetailsException e) {
            return ResponseEntity.status(e.getStatus()).body("Error updating SimDetails: " + e.getMessage());
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body("Invalid request format. Please provide valid SimDetails data.");
        }
    }

    @DeleteMapping("/deleteSim/{id}")
    public String deleteSimDetails(@PathVariable Long id) {
        try {
            simDetailsService.deleteSimDetails(id);
            return "SimDetails deleted successfully.";
        } catch (Exception e) {
            return "Error deleting SimDetails: " + e.getMessage();
        }
    }
    //endregion
}
