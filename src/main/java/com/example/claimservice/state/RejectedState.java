package com.example.claimservice.state;

import com.example.claimservice.entity.Claim;
import com.example.claimservice.entity.ClaimStatus;

public class RejectedState implements ClaimState {

    @Override
    public void approve(Claim claim) {
        throw new IllegalStateException("Cannot approve a rejected claim");
    }

    @Override
    public void reject(Claim claim) {
        throw new IllegalStateException("Claim is already rejected");
    }

    @Override
    public void cancel(Claim claim, String reason) {
        throw new IllegalStateException("Cannot cancel a rejected claim");
    }

    @Override
    public ClaimStatus getStatus() {
        return ClaimStatus.REJECTED;
    }
}