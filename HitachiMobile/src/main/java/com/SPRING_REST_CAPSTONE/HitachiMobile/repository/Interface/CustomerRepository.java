package com.SPRING_REST_CAPSTONE.HitachiMobile.repository.Interface;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.CustomInterface.CustomerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {

    Customer findByEmailAddressAndDateOfBirth(String emailAddress, String dateOfBirth);

    @Query("SELECT c FROM Customer c WHERE c.uniqueIdNumber = ?1")
    Customer findByUniqueIdNumber(Long uniqueIdNumber);

    @Query("SELECT c FROM Customer c WHERE c.firstName = ?1 AND c.lastName = ?2 AND c.dateOfBirth = ?3")
    Customer findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, String dateOfBirth);
}
