package com.SPRING_REST_CAPSTONE.HitachiMobile.controller;

import com.SPRING_REST_CAPSTONE.HitachiMobile.dto.*;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.CustomerAddress;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.InvalidDetailsException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sim")
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    //region Crud Operations
    @GetMapping("/get/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestBody Object customer)
    {
        try {
            customerService.updateCustomer(id, (Customer) customer);
            return "Customer updated successfully.";
        } catch (Exception e) {
            return "Error updating customer: " + e.getMessage();
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return "Customer deleted successfully.";
        } catch (Exception e) {
            return "Error deleting customer: " + e.getMessage();
        }
    }
    //endregion


    @PostMapping("/validate")
    public ResponseEntity<SimValidationResponse> validateSim(@RequestBody SimValidationRequest request) {
        try {
            String offerDetails = customerService.validateSimAndGetOffers(
                    request.getSimNumber(),
                    request.getServiceNumber()
            );
            SimValidationResponse response = new SimValidationResponse(offerDetails, true);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            SimValidationResponse response = new SimValidationResponse(e.getMessage(), false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/validate-customer")
    public ResponseEntity<CustomerValidationResponse> validateCustomerDetails(@RequestBody CustomerValidationRequest request) {
        try {
            String message = customerService.validateCustomerBasicDetails(
                    request.getEmailAddress(),
                    request.getDateOfBirth()
            );
            CustomerValidationResponse response = new CustomerValidationResponse(message, true);
            return ResponseEntity.ok(response);

        } catch (InvalidDetailsException e) {
            CustomerValidationResponse response = new CustomerValidationResponse(e.getMessage(), false);
            return ResponseEntity.status(e.getStatus()).body(response);
        }
    }

    @PutMapping("/update-address")
    public ResponseEntity<AddressUpdateResponse> updateCustomerAddress(@RequestBody AddressUpdateRequest request) {
        try {
            CustomerAddress updatedAddress = customerService.updateCustomerAddress(request);
            AddressUpdateResponse response = new AddressUpdateResponse(
                    "Address updated successfully",
                    true,
                    updatedAddress
            );
            return ResponseEntity.ok(response);

        } catch (InvalidDetailsException e) {
            AddressUpdateResponse response = new AddressUpdateResponse(e.getMessage(), false, null);
            return ResponseEntity.status(e.getStatus()).body(response);
        }
    }

    @PostMapping("/validate-id-proof")
    public ResponseEntity<IdProofValidationResponse> validateIdProof(@RequestBody IdProofValidationRequest request) {
        try {
            String message = customerService.validateCustomerIdProof(request);
            IdProofValidationResponse response = new IdProofValidationResponse(message, true);
            return ResponseEntity.ok(response);

        } catch (InvalidDetailsException e) {
            IdProofValidationResponse response = new IdProofValidationResponse(e.getMessage(), false);
            return ResponseEntity.status(e.getStatus()).body(response);
        }
    }


}