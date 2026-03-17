package se.lexicon.subscriptionapi.service;

import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;

import java.util.List;

public interface PlanService {

    PlanResponse createPlan(PlanRequest planRequest);
    PlanResponse updatePlan(Long id ,PlanRequest planRequest);
    void deletePlan(Long id);
    List<PlanResponse> findAll();
    List<PlanResponse> findActivePlan();

}
