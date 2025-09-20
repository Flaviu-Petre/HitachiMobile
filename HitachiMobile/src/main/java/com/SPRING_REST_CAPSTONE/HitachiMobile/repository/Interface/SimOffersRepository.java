package com.SPRING_REST_CAPSTONE.HitachiMobile.repository.Interface;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimOffers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimOffersRepository extends CrudRepository<SimOffers, Long> {

    @Query ("SELECT s FROM SimOffers s WHERE s.simId = ?1")
    List<SimOffers> findBySimId(Long simId);
}
