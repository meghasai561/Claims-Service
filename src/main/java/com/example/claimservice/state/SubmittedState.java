package com.example.claimservice.state;

import com.example.claimservice.entity.Claim;
import com.example.claimservice.entity.ClaimStatus;

public class SubmittedState implements ClaimState {

    @Override
    public void approve(Claim claim) {
        claim.setStatus(ClaimStatus.APPROVED);
    }

    @Override
    public void reject(Claim claim) {
        claim.setStatus(ClaimStatus.REJECTED);
    }

    @Override
    public void cancel(Claim claim, String reason) {
        claim.setStatus(ClaimStatus.CANCELLED);
        claim.setCancelReason(reason);
    }

    @Override
    public ClaimStatus getStatus() {
        return ClaimStatus.SUBMITTED;
    }
}