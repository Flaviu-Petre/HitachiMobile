package com.SPRING_REST_CAPSTONE.HitachiMobile.repository;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SimDetailsRepository extends JpaRepository<SimDetails, Integer> {

    @Query ("SELECT s FROM SimDetails s WHERE s.simNumber = ?1 AND s.serviceNumber = ?2")
    SimDetails findBySimNumberAndServiceNumber(String simNumber, String serviceNumber);
}
