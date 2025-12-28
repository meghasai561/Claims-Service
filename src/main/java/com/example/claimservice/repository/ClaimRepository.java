package com.example.claimservice.repository;

import com.example.claimservice.entity.Claim;
import com.example.claimservice.entity.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long>, JpaSpecificationExecutor<Claim> {

    List<Claim> findByPolicyNumberAndStatus(String policyNumber, ClaimStatus status);

    List<Claim> findByPolicyNumber(String policyNumber);

    List<Claim> findByStatus(ClaimStatus status);
}