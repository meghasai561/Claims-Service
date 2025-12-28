package com.example.claimservice.state;

import com.example.claimservice.entity.ClaimStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ClaimStateFactory {

    private final Map<ClaimStatus, ClaimState> states = new HashMap<>();

    public ClaimStateFactory() {
        states.put(ClaimStatus.SUBMITTED, new SubmittedState());
        states.put(ClaimStatus.APPROVED, new ApprovedState());
        states.put(ClaimStatus.REJECTED, new RejectedState());
        states.put(ClaimStatus.CANCELLED, new CancelledState());
    }

    public ClaimState getState(ClaimStatus status) {
        return states.get(status);
    }
}