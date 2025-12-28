package com.example.claimservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @Column(name = "policy_number", unique = true, nullable = false)
    private String policyNumber;

    @Column(name = "medical_limit", nullable = false)
    private BigDecimal medicalLimit;

    @Column(name = "dental_limit", nullable = false)
    private BigDecimal dentalLimit;

    @Column(name = "travel_limit", nullable = false)
    private BigDecimal travelLimit;

    // Constructors
    public Policy() {}

    public Policy(String policyNumber, BigDecimal medicalLimit, BigDecimal dentalLimit, BigDecimal travelLimit) {
        this.policyNumber = policyNumber;
        this.medicalLimit = medicalLimit;
        this.dentalLimit = dentalLimit;
        this.travelLimit = travelLimit;
    }

    // Getters and Setters
    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public BigDecimal getMedicalLimit() {
        return medicalLimit;
    }

    public void setMedicalLimit(BigDecimal medicalLimit) {
        this.medicalLimit = medicalLimit;
    }

    public BigDecimal getDentalLimit() {
        return dentalLimit;
    }

    public void setDentalLimit(BigDecimal dentalLimit) {
        this.dentalLimit = dentalLimit;
    }

    public BigDecimal getTravelLimit() {
        return travelLimit;
    }

    public void setTravelLimit(BigDecimal travelLimit) {
        this.travelLimit = travelLimit;
    }
}