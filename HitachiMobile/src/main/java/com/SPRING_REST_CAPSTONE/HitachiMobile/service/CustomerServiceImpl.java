package com.SPRING_REST_CAPSTONE.HitachiMobile.service;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimDetails;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimOffers;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.InvalidDetailsException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.CustomerRepository;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.SimDetailsRepository;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.SimOffersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements  CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SimDetailsRepository simDetailsRepository;

    @Autowired
    private SimOffersRepository simOffersRepository;


    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmailAddress(customer.getEmailAddress());
        existingCustomer.setDateOfBirth(customer.getDateOfBirth());
        existingCustomer.setIdType(customer.getIdType());
        existingCustomer.setState(customer.getState());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public String validateSimAndGetOffers(String simNumber, String serviceNumber) {

        if (simNumber == null || !simNumber.matches("\\d{13}")) {
            throw new InvalidDetailsException("SIM number must be exactly 13 digits");
        }

        if (serviceNumber == null || !serviceNumber.matches("\\d{10}")) {
            throw new InvalidDetailsException("Service number must be exactly 10 digits");
        }

        SimDetails simDetails = simDetailsRepository.findBySimNumberAndServiceNumber(simNumber, serviceNumber);

        if (simDetails == null) {
            throw new InvalidDetailsException("Invalid SIM/Service number combination not found", HttpStatus.NOT_FOUND);
        }

        if ("active".equalsIgnoreCase(simDetails.getSimStatus())) {
            throw new InvalidDetailsException("Subscriber Identity Module (SIM) already active");
        }

        List<SimOffers> offers = simOffersRepository.findBySimId(simDetails.getSimId());

        if (offers == null || offers.isEmpty()) {
            throw new InvalidDetailsException("No offers available for this SIM");
        }

        SimOffers offer = offers.get(0);

        return String.format("%.0f calls + %.0f GB for Rs.%.0f, Validity: %d days",
                offer.getCallQty(),
                offer.getDataQty(),
                offer.getCost(),
                offer.getDuration());
    }

    @Override
    public String validateCustomerBasicDetails(String emailAddress, String dateOfBirth) {
        if (emailAddress == null || emailAddress.trim().isEmpty()) {
            throw new InvalidDetailsException("Email/dob value is required");
        }

        if (dateOfBirth == null || dateOfBirth.trim().isEmpty()) {
            throw new InvalidDetailsException("Email/dob value is required");
        }

        if (!dateOfBirth.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new InvalidDetailsException("dob should be in yyyy-mm-dd format");
        }

        if (!emailAddress.matches("^[^@]+@[^@]+\\.[a-zA-Z]{2,3}$")) {
            throw new InvalidDetailsException("Invalid email");
        }

        Customer customer = customerRepository.findByEmailAddressAndDateOfBirth(emailAddress, dateOfBirth);

        if (customer == null) {
            throw new InvalidDetailsException("No request placed for you", HttpStatus.NOT_FOUND);
        }

        return "Customer validation successful";
    }
}
