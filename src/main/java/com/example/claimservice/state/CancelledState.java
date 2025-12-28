package com.example.claimservice.state;

import com.example.claimservice.entity.Claim;
import com.example.claimservice.entity.ClaimStatus;

public class CancelledState implements ClaimState {

    @Override
    public void approve(Claim claim) {
        throw new IllegalStateException("Cannot approve a cancelled claim");
    }

    @Override
    public void reject(Claim claim) {
        throw new IllegalStateException("Cannot reject a cancelled claim");
    }

    @Override
    public void cancel(Claim claim, String reason) {
        throw new IllegalStateException("Claim is already cancelled");
    }

    @Override
    public ClaimStatus getStatus() {
        return ClaimStatus.CANCELLED;
    }
}