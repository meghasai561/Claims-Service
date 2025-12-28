package com.example.claimservice.state;

import com.example.claimservice.entity.Claim;
import com.example.claimservice.entity.ClaimStatus;

public interface ClaimState {

    void approve(Claim claim);

    void reject(Claim claim);

    void cancel(Claim claim, String reason);

    ClaimStatus getStatus();
}