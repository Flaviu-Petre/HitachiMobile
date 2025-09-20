package com.SPRING_REST_CAPSTONE.HitachiMobile.service;

import com.SPRING_REST_CAPSTONE.HitachiMobile.dto.AddressUpdateRequest;
import com.SPRING_REST_CAPSTONE.HitachiMobile.dto.IdProofValidationRequest;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.*;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.InvalidDetailsException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.Interface.*;
import com.SPRING_REST_CAPSTONE.HitachiMobile.service.Interface.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    //region Repositories
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SimDetailsRepository simDetailsRepository;

    @Autowired
    private SimOffersRepository simOffersRepository;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private CustomerIdentityRepository customerIdentityRepository;
    //endregion

    //region CRUD Operations
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new InvalidDetailsException("Customer not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new InvalidDetailsException("Customer not found with id: " + id));

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
    //endregion


    //region Custom Methods
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

    @Override
    public CustomerAddress updateCustomerAddress(AddressUpdateRequest request) {

        if (request.getAddress() == null || request.getAddress().length() > 25) {
            throw new InvalidDetailsException("Address should be maximum of 25 characters");
        }

        if (request.getZipCode() == null || !request.getZipCode().matches("\\d{6}")) {
            throw new InvalidDetailsException("Pin should be 6 digit number");
        }

        if (request.getCity() == null || !request.getCity().matches("^[a-zA-Z\\s]+$")) {
            throw new InvalidDetailsException("City/State should not contain any special characters except space");
        }

        if (request.getState() == null || !request.getState().matches("^[a-zA-Z\\s]+$")) {
            throw new InvalidDetailsException("City/State should not contain any special characters except space");
        }

        Customer customer = customerRepository.findByUniqueIdNumber(request.getCustomerId());
        if (customer == null) {
            throw new InvalidDetailsException("Customer Not Found", HttpStatus.NOT_FOUND);
        }

        CustomerAddress address = customer.getCustomerAddress();
        if (address == null) {
            address = new CustomerAddress();
            address.setAddress(request.getAddress());
            address.setCity(request.getCity());
            address.setState(request.getState());
            address.setZipCode(request.getZipCode());
            address = customerAddressRepository.save(address);

            customer.setCustomerAddress(address);
            customerRepository.save(customer);
        } else {
            address.setAddress(request.getAddress());
            address.setCity(request.getCity());
            address.setState(request.getState());
            address.setZipCode(request.getZipCode());
            address = customerAddressRepository.save(address);
        }

        return address;
    }

    @Override
    public String validateCustomerIdProof(IdProofValidationRequest request) {

        if (request.getAadharNumber() == null || !request.getAadharNumber().matches("\\d{16}")) {
            throw new InvalidDetailsException("Id should be 16 digits");
        }

        if (request.getDateOfBirth() == null || !request.getDateOfBirth().matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new InvalidDetailsException("Invalid details");
        }

        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty() ||
                request.getLastName() == null || request.getLastName().trim().isEmpty()) {
            throw new InvalidDetailsException("Invalid details");
        }

        Customer customer = customerRepository.findByFirstNameAndLastNameAndDateOfBirth(
                request.getFirstName(),
                request.getLastName(),
                request.getDateOfBirth()
        );

        if (customer == null) {
            throw new InvalidDetailsException("Customer Not Found", HttpStatus.NOT_FOUND);
        }

        CustomerIdentity existingIdentity = customerIdentityRepository.findByUniqueIdNumber(customer.getUniqueIdNumber());

        if (existingIdentity == null) {
            CustomerIdentity identity = new CustomerIdentity();
            identity.setUniqueIdNumber(customer.getUniqueIdNumber());
            identity.setFirstName(customer.getFirstName());
            identity.setLastName(customer.getLastName());
            identity.setEmailAddress(customer.getEmailAddress());
            identity.setDateOfBirth(customer.getDateOfBirth());
            identity.setState(customer.getState());
            identity.setCustomer(customer);
            customerIdentityRepository.save(identity);
        }

        List<SimDetails> simDetailsList = simDetailsRepository.findByCustomer(customer);
        if (simDetailsList != null && !simDetailsList.isEmpty()) {
            for (SimDetails simDetails : simDetailsList) {
                simDetails.setSimStatus("active");
                simDetailsRepository.save(simDetails);
            }
        }

        return "ID proof validation successful and SIM activated";
    }

    @Override
    public List<Customer> getCustomersInBangalore() {
        List<Customer> customers = customerRepository.findCustomersInBangalore();

        return customers.stream()
                .filter(customer -> customer.getFirstName() != null)
                .sorted(Comparator.comparing(Customer::getFirstName))
                .collect(Collectors.toList());
    }
    //endregion
}
