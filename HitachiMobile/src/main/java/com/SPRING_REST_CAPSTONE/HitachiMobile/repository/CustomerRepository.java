package com.SPRING_REST_CAPSTONE.HitachiMobile.repository;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmailAddressAndDateOfBirth(String emailAddress, String dateOfBirth);

    @Query("SELECT c FROM Customer c WHERE c.uniqueIdNumber = ?1")
    Customer findByUniqueIdNumber(Long uniqueIdNumber);
}
