package com.SPRING_REST_CAPSTONE.HitachiMobile.repository;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimOffers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimOffersRepository extends CrudRepository<SimOffers, Integer> {

    @Query ("SELECT s FROM SimOffers s WHERE s.simId = ?1")
    SimOffers findBySimId(Integer simId);
}
