package com.example.claimservice.state;

import com.example.claimservice.entity.Claim;
import com.example.claimservice.entity.ClaimStatus;

public class ApprovedState implements ClaimState {

    @Override
    public void approve(Claim claim) {
        throw new IllegalStateException("Claim is already approved");
    }

    @Override
    public void reject(Claim claim) {
        throw new IllegalStateException("Cannot reject an approved claim");
    }

    @Override
    public void cancel(Claim claim, String reason) {
        throw new IllegalStateException("Cannot cancel an approved claim");
    }

    @Override
    public ClaimStatus getStatus() {
        return ClaimStatus.APPROVED;
    }
}