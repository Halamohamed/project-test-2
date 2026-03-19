package se.lexicon.subscriptionapi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;
import se.lexicon.subscriptionapi.exception.BusinessRuleException;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.PlanMapper;
import se.lexicon.subscriptionapi.repository.OperatorRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.service.PlanService;

import java.util.List;
@Service
public class PlanServiceImpl implements PlanService {

    private final PlanMapper planMapper;
    private final OperatorRepository operatorRepository;
    private final PlanRepository planRepository;

    public PlanServiceImpl(PlanMapper planMapper,
                           OperatorRepository operatorRepository,
                           PlanRepository planRepository) {
        this.planMapper = planMapper;
        this.operatorRepository = operatorRepository;
        this.planRepository = planRepository;
    }

    @Override
    @Transactional
    public PlanResponse createPlan(PlanRequest planRequest) {
        Operator operator = operatorRepository.findById(planRequest.operatorId())
                .orElseThrow(()-> new ResourceNotFoundException("Operator not found"));
        boolean exists = planRepository.existsByNameAndOperatorId(
                planRequest.name(), planRequest.operatorId());
        if (exists){
            throw new BusinessRuleException("Plan name already exists for this operator");
        }
        Plan plan = planMapper.toEntity(planRequest);
        plan.setOperator(operator);
        Plan savedPlan =planRepository.save(plan);

        return planMapper.toResponse(savedPlan);
    }

    @Override
    @Transactional
    public PlanResponse updatePlan(Long id, PlanRequest planRequest) {
       Plan plan = planRepository.findById(id)
               .orElseThrow(()-> new ResourceNotFoundException("Plan not found"));
       Operator operator = operatorRepository.findById(planRequest.operatorId())
               .orElseThrow(()-> new ResourceNotFoundException("Operator not found"));

       boolean exists = planRepository.existsByNameAndOperatorIdAndIdNot(
               planRequest.name(), planRequest.operatorId(), id);

       if (exists){
           throw new BusinessRuleException("Another plan with this name already exists for this operator");
       }
       plan.setName(planRequest.name());
       plan.setPrice(planRequest.price());
       plan.setServiceType(planRequest.serviceType());
       plan.setDataLimit(planRequest.dataLimit());
       plan.setActive(planRequest.isActive());
       plan.setOperator(operator);

        return planMapper.toResponse(plan);

    }

    @Override
    @Transactional
    public void deletePlan(Long id) {
        if(!planRepository.existsById(id)){
            throw new ResourceNotFoundException("Plan not found");
        }
        planRepository.deleteById(id);

    }

    @Transactional(readOnly = true)
    @Override
    public List<PlanResponse> findAll() {
        return planRepository.findAll()
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findActivePlan() {
        return planRepository.findByIsActiveTrue()
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findActiveByServiceType(ServiceType serviceType) {
        return planRepository.findByIsActiveTrueAndServiceType(serviceType).stream()
                .map(planMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findByOperator(Long operatorId) {
        return planRepository.findByOperatorIdAndIsActiveTrue(operatorId)
                .stream().map(planMapper::toResponse)
                .toList();
    }
}
