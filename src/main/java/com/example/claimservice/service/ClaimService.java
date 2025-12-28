package com.example.claimservice.service;

import com.example.claimservice.entity.*;
import com.example.claimservice.repository.ClaimRepository;
import com.example.claimservice.repository.PolicyRepository;
import com.example.claimservice.state.ClaimState;
import com.example.claimservice.state.ClaimStateFactory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ClaimStateFactory stateFactory;

    @Transactional
    public Claim createClaim(Claim claim) {
        // Validate policy exists
        Policy policy = policyRepository.findById(claim.getPolicyNumber())
                .orElseThrow(() -> new IllegalArgumentException("Policy not found"));

        // Validate coverage
        validateCoverage(claim, policy);

        // Validate description length
        if (claim.getDescription() != null && claim.getDescription().length() > 500) {
            throw new IllegalArgumentException("Description too long");
        }

        return claimRepository.save(claim);
    }

    private void validateCoverage(Claim claim, Policy policy) {
        BigDecimal limit = switch (claim.getClaimType()) {
            case MEDICAL -> policy.getMedicalLimit();
            case DENTAL -> policy.getDentalLimit();
            case TRAVEL -> policy.getTravelLimit();
        };

        if (claim.getAmount().compareTo(limit) > 0) {
            throw new IllegalArgumentException("Claim amount exceeds policy limit for " + claim.getClaimType());
        }
    }

    public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }

    public List<Claim> searchClaims(String policyNumber, ClaimStatus status) {
        return claimRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (policyNumber != null) {
                predicates.add(criteriaBuilder.equal(root.get("policyNumber"), policyNumber));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Transactional
    public Claim updateClaimStatus(Long id, ClaimStatus newStatus) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        ClaimState currentState = stateFactory.getState(claim.getStatus());

        switch (newStatus) {
            case APPROVED -> currentState.approve(claim);
            case REJECTED -> currentState.reject(claim);
            default -> throw new IllegalArgumentException("Invalid status transition");
        }

        return claimRepository.save(claim);
    }

    @Transactional
    public Claim cancelClaim(Long id, String reason) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        ClaimState currentState = stateFactory.getState(claim.getStatus());
        currentState.cancel(claim, reason);

        return claimRepository.save(claim);
    }
}