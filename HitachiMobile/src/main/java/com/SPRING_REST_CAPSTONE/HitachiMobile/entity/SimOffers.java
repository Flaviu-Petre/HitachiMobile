package com.SPRING_REST_CAPSTONE.HitachiMobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "sim_offers")
public class SimOffers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long offerId;

    @Column(name = "call_qty", nullable = false)
    private Double callQty;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "data_qty", nullable = false)
    private Double dataQty;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "offer_name", nullable = false)
    private String offerName;

    @Column(name = "sim_id", nullable = false)
    private Integer simId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sim_id", referencedColumnName = "sim_id", insertable = false, updatable = false)
    @JsonIgnore
    private SimDetails simDetails;
}