package se.lexicon.subscriptionapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(@RequestBody @Valid PlanRequest planRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.createPlan(planRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> updatePlan(@PathVariable Long id,
                                                   @RequestBody @Valid PlanRequest planRequest) {
        return ResponseEntity.ok(planService.updatePlan(id, planRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<PlanResponse> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PlanResponse>> findAllPlan() {
        return ResponseEntity.ok(planService.findAll());
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/active")
    public ResponseEntity<List<PlanResponse>> findActivePlan() {
        return ResponseEntity.ok(planService.findActivePlan());
    }
}
