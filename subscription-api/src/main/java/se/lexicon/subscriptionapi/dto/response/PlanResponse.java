package se.lexicon.subscriptionapi.dto.response;

import se.lexicon.subscriptionapi.domain.constant.ServiceType;

import java.math.BigDecimal;

public record PlanResponse(
        Long id,
        String name,
        double price,
        ServiceType serviceType,
        Integer dataLimit,
        boolean isActive,
        String operatorName
) {
}
