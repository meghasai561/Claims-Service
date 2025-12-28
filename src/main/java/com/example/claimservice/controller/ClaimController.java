package com.example.claimservice.controller;

import com.example.claimservice.entity.Claim;
import com.example.claimservice.entity.ClaimStatus;
import com.example.claimservice.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping
    public ResponseEntity<Claim> createClaim(@Valid @RequestBody Claim claim) {
        try {
            Claim createdClaim = claimService.createClaim(claim);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClaim);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> getClaimById(@PathVariable Long id) {
        Optional<Claim> claim = claimService.getClaimById(id);
        return claim.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Claim>> searchClaims(
            @RequestParam(required = false) String policyNumber,
            @RequestParam(required = false) ClaimStatus status) {
        List<Claim> claims = claimService.searchClaims(policyNumber, status);
        return ResponseEntity.ok(claims);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Claim> updateClaimStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            ClaimStatus newStatus = ClaimStatus.valueOf(request.get("status"));
            Claim updatedClaim = claimService.updateClaimStatus(id, newStatus);
            return ResponseEntity.ok(updatedClaim);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Claim> cancelClaim(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String reason = request.get("reason");
            Claim cancelledClaim = claimService.cancelClaim(id, reason);
            return ResponseEntity.ok(cancelledClaim);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}