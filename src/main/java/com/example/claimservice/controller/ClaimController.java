package com.example.claimservice.controller;

import com.example.claimservice.entity.Claim;
import com.example.claimservice.entity.ClaimStatus;
import com.example.claimservice.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping
    public ResponseEntity<Claim> createClaim(@Valid @RequestBody Claim claim) {
        Claim createdClaim = claimService.createClaim(claim);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClaim);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getClaimById(@PathVariable Long id) {
        Optional<Claim> claim = claimService.getClaimById(id);
        if (claim.isPresent()) {
            return ResponseEntity.ok(claim.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Claim not found"));
        }
    }

    @GetMapping
    public ResponseEntity<List<Claim>> searchClaims(
            @RequestParam(required = false) String policyNumber,
            @RequestParam(required = false) ClaimStatus status) {
        List<Claim> claims = claimService.searchClaims(policyNumber, status);
        return ResponseEntity.ok(claims);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Object> updateClaimStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            ClaimStatus newStatus = ClaimStatus.valueOf(request.get("status"));
            Claim updatedClaim = claimService.updateClaimStatus(id, newStatus);
            return ResponseEntity.ok(updatedClaim);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Claim not found"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid status value"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Claim not found"));
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Object> cancelClaim(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String reason = request.get("reason");
            Claim cancelledClaim = claimService.cancelClaim(id, reason);
            return ResponseEntity.ok(cancelledClaim);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Claim not found"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Cannot cancel claim in its current status"));
        }
    }
}