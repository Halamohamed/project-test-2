package se.lexicon.subscriptionapi.service;

import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;

public interface SubscriptionService {

    SubscriptionResponse subscribe(Long id, SubscriptionRequest request);

    SubscriptionResponse changePlan(Long subscriptionId, Long newPlanId);

    void cancel(Long subscriptionId);
}
