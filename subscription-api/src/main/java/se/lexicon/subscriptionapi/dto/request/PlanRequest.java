package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;

import java.math.BigDecimal;

public record PlanRequest(
        @NotBlank String name,
        @NotNull double price,
        @NotBlank ServiceType serviceType,
        Integer dataLimit,
        boolean isActive,
        @NotNull Long operatorId
) {
}
