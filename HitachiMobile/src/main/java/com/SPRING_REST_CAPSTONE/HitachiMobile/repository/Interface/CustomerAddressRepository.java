package com.SPRING_REST_CAPSTONE.HitachiMobile.repository.Interface;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Integer> {
}
