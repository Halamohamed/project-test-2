package se.lexicon.subscriptionapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;
import se.lexicon.subscriptionapi.service.PlanService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a Plan", description = "\"Requires JWT.\\n\\nRole: ADMIN\"")
    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(@RequestBody @Valid PlanRequest planRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.createPlan(planRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a Plan", description = "\"Requires JWT.\\n\\nRole: ADMIN\"")
    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> updatePlan(@PathVariable Long id,
                                                   @RequestBody @Valid PlanRequest planRequest) {
        return ResponseEntity.ok(planService.updatePlan(id, planRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "delete a plan", description = "\"Requires JWT.\\n\\nRole: ADMIN\"")
    @DeleteMapping("/{id}")
    public ResponseEntity<PlanResponse> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get All Plans", description = "Requires JWT.\n\nRole:ADMIN")
    @GetMapping
    public ResponseEntity<List<PlanResponse>> findAllPlan() {
        return ResponseEntity.ok(planService.findAll());
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Get Active Plan", description = "\"Requires JWT.\\n\\nRoles: USER, ADMIN\"")
    @GetMapping("/active")
    public ResponseEntity<List<PlanResponse>> findActivePlan() {
        return ResponseEntity.ok(planService.findActivePlan());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get a Plan By Service Type", description = "\"Requires JWT.\\n\\nRoles: USER, ADMIN\"")
    @GetMapping("/active/{serviceType}")
    public ResponseEntity<List<PlanResponse>> findByServiceType(@PathVariable ServiceType serviceType) {
        return ResponseEntity.ok(planService.findActiveByServiceType(serviceType));
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get aPlan By Operator Id", description = "\"Requires JWT.\\n\\nRole: USER\"")
    @GetMapping("/operator/{operatorId}")
    public ResponseEntity<List<PlanResponse>> findByOperator(@PathVariable Long operatorId) {
      return ResponseEntity.ok(planService.findByOperator(operatorId));
    }
}
