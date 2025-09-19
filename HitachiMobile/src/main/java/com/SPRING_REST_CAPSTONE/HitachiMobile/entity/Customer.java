package com.SPRING_REST_CAPSTONE.HitachiMobile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    //region fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unique_id_number")
    private Long uniqueIdNumber;

    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "id_type", nullable = false)
    private String idType;

    @Column(name = "sim_id", nullable = false)
    private Integer simId;

    @Column(name = "state", nullable = false)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_address_id", referencedColumnName = "address_id")
    private CustomerAddress customerAddress;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SimDetails> simDetailsList;

    //endregion
}