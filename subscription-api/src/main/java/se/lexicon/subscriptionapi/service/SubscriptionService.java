package se.lexicon.subscriptionapi.service;

import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {

    SubscriptionResponse subscribe(Long id, SubscriptionRequest request);

    SubscriptionResponse changePlan(Long subscriptionId, Long newPlanId);

    void cancel(Long subscriptionId);

    List<SubscriptionResponse> findByCustomer(Long customerId);
}
