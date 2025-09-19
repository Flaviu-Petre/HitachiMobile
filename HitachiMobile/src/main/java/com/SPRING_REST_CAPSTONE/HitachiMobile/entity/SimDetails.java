package com.SPRING_REST_CAPSTONE.HitachiMobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "sim_details")
public class SimDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sim_id")
    private Integer simId;

    @Column(name = "service_number", nullable = false, unique = true)
    private String serviceNumber;

    @Column(name = "sim_number", nullable = false, unique = true)
    private String simNumber;

    @Column(name = "sim_status", nullable = false)
    private String simStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "unique_id_number")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "simDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SimOffers> simOffersList;
}