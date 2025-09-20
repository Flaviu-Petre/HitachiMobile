package com.SPRING_REST_CAPSTONE.HitachiMobile.repository;

import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.CustomerTableEmptyException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.CustomInterface.CustomerRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findCustomersInBangalore() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> customer = cq.from(Customer.class);

        Predicate statePredicate = cb.equal(customer.get("state"), "Bangalore");

        cq.select(customer)
                .where(statePredicate)
                .orderBy(cb.asc(customer.get("firstName")));

        return session.createQuery(cq).getResultList();
    }

    @Override
    public List<Customer> findAllCustomers() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> customer = cq.from(Customer.class);

        cq.select(customer)
                .orderBy(cb.asc(customer.get("customerId")));

        List<Customer> result = session.createQuery(cq).getResultList();

        if (result.isEmpty()) {
            throw new CustomerTableEmptyException("No customers found in the database.");
        }

        return result;
    }
}
