package com.SPRING_REST_CAPSTONE.HitachiMobile.repository;

import com.SPRING_REST_CAPSTONE.HitachiMobile.dto.AddressUpdateRequest;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.Customer;
import com.SPRING_REST_CAPSTONE.HitachiMobile.entity.CustomerAddress;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.CustomerDoesNotExistException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.CustomerTableEmptyException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.exception.InvalidDetailsException;
import com.SPRING_REST_CAPSTONE.HitachiMobile.repository.CustomInterface.CustomerRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
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

    @Override
    public CustomerAddress updateCustomerAddress(AddressUpdateRequest request) {
        if (request.getAddress() == null || request.getAddress().length() > 25) {
            throw new InvalidDetailsException("Address should be maximum of 25 characters");
        }

        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> customerRoot = cq.from(Customer.class);
        Predicate customerPredicate = cb.equal(customerRoot.get("uniqueIdNumber"), request.getCustomerId());

        cq.select(customerRoot).where(customerPredicate);

        try {
            Customer customer = session.createQuery(cq).getSingleResult();

            CustomerAddress address = customer.getCustomerAddress();
            if (address == null) {
                address = new CustomerAddress();
                address.setAddress(request.getAddress());
                address.setCity(request.getCity());
                address.setState(request.getState());
                address.setZipCode(request.getZipCode());
                session.persist(address);
            } else {
                address.setAddress(request.getAddress());
                address.setCity(request.getCity());
                address.setState(request.getState());
                address.setZipCode(request.getZipCode());
                session.merge(address);
            }

            return address;

        } catch (NoResultException e) {
            throw new CustomerDoesNotExistException("No customer found for given unique Id");
        }
    }
}
