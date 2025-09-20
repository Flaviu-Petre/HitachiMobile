package com.SPRING_REST_CAPSTONE.HitachiMobile.controller;

import com.SPRING_REST_CAPSTONE.HitachiMobile.dto.*;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.CustomerAddress;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.InvalidDetailsException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.service.Interface.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/Customer")
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
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (InvalidDetailsException e) {
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            customerService.updateCustomer(id, customer);
            return ResponseEntity.ok("Customer updated successfully.");
        } catch (InvalidDetailsException e) {
            return ResponseEntity.status(e.getStatus()).body("Error updating customer: " + e.getMessage());
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body("Invalid request format. Please provide valid customer data.");
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

    @GetMapping("/customers/bangalore")
    public ResponseEntity<List<Customer>> getCustomersInBangalore() {
        try {
            List<Customer> customers = customerService.getCustomersInBangalore();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

}