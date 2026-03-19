package se.lexicon.subscriptionapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;
import se.lexicon.subscriptionapi.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscripions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "subscribe ", description = "Requires JWT.\\n\\nRoles: USER, ADMIN\"" )
    @PostMapping
    public ResponseEntity<SubscriptionResponse> subscribe(@RequestParam Long customerId,
                                                          @RequestBody SubscriptionRequest subscriptionRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionService.subscribe(customerId, subscriptionRequest));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "find subscription by customer", description = "Requires JWT.\\n\\nRoles: USER, ADMIN\"")
    public ResponseEntity<List<SubscriptionResponse>> findByCustomerId(@PathVariable Long customerId) {
       return ResponseEntity.ok(subscriptionService.findByCustomer(customerId));
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "change a plan", description = "Requires JWT.\\n\\nRole: USER\"")
    @PutMapping("/{subscriptionId}/change/{newPlanId}")
    public ResponseEntity<SubscriptionResponse> changePlan(@PathVariable Long subscriptionId,
                                                           @PathVariable Long newPlanId) {

        return ResponseEntity.status(HttpStatus.OK).body(subscriptionService.changePlan(subscriptionId, newPlanId));
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{subscriptionId}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long subscriptionId) {
        subscriptionService.cancel(subscriptionId);
        return ResponseEntity.noContent().build();
    }
}
