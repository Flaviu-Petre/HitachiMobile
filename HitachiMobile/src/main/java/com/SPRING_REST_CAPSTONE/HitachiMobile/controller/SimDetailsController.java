package com.SPRING_REST_CAPSTONE.HitachiMobile.controller;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimDetails;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.InvalidDetailsException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.service.Interface.SimDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/simDetails")
public class SimDetailsController {

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

    @PutMapping("/assign-sim/{simId}/customer/{customerId}")
    public ResponseEntity<String> assignSimToCustomer(@PathVariable Long simId, @PathVariable Long customerId) {
        try {
            simDetailsService.assignSimToCustomer(simId, customerId);
            return ResponseEntity.ok("SIM assigned to customer successfully");
        } catch (InvalidDetailsException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/active-sims")
    public ResponseEntity<List<SimDetails>> getActiveSimDetails() {
        try {
            List<SimDetails> activeSimDetails = simDetailsService.getActiveSimDetails();
            return ResponseEntity.ok(activeSimDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
}
