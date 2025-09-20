package com.SPRING_REST_CAPSTONE.HitachiMobile.repository;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.SimDetails;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.CustomInterface.SimDetailsRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimDetailsRepositoryImpl implements SimDetailsRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SimDetails> findActiveSimDetails() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<SimDetails> cq = cb.createQuery(SimDetails.class);

        Root<SimDetails> simDetails = cq.from(SimDetails.class);

        Predicate activePredicate = cb.equal(simDetails.get("simStatus"), "active");

        cq.select(simDetails)
                .where(activePredicate)
                .orderBy(cb.asc(simDetails.get("simId")));

        return session.createQuery(cq).getResultList();
    }
}
