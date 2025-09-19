package com.SPRING_REST_CAPSTONE.HitachiMobile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_identity")
public class CustomerIdentity {

    @Id
    @Column(name = "unique_id_number")
    private Long uniqueIdNumber;

    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "state", nullable = false)
    private String state;

    @OneToOne
    @MapsId
    @JoinColumn(name = "unique_id_number")
    private Customer customer;
}