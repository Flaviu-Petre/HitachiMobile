package com.SPRING_REST_CAPSTONE.HitachiMobile.repository;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.CustomerIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerIdentityRepository extends JpaRepository<CustomerIdentity, Long> {

    @Query("SELECT ci FROM CustomerIdentity ci WHERE ci.uniqueIdNumber = ?1")
    CustomerIdentity findByUniqueIdNumber(Long uniqueIdNumber);
}
