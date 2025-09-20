package com.SPRING_REST_CAPSTONE.HitachiMobile.repository.CustomInterface;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimDetails;

import java.util.List;

public interface SimDetailsRepositoryCustom {
    List<SimDetails> findActiveSimDetails();
}
