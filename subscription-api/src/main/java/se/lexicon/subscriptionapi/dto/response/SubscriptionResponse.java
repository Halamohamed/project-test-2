package se.lexicon.subscriptionapi.dto.response;

import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;

import java.time.LocalDateTime;

public record SubscriptionResponse(
        Long id,
        String planName,
        ServiceType serviceType,
        SubscriptionStatus status,
        LocalDateTime createdAt,
        LocalDateTime cancelledAt
) {
}
