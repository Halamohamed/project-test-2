package se.lexicon.subscriptionapi.service.impl;

import org.springframework.stereotype.Service;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;
import se.lexicon.subscriptionapi.domain.entity.Customer;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;
import se.lexicon.subscriptionapi.exception.BusinessRuleException;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.SubscriptionMapper;
import se.lexicon.subscriptionapi.repository.CustomerRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.repository.SubscriptionRepository;
import se.lexicon.subscriptionapi.service.SubscriptionService;

import java.time.LocalDateTime;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final CustomerRepository customerRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   PlanRepository planRepository,
                                   CustomerRepository customerRepository,
                                   SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.customerRepository = customerRepository;
        this.subscriptionMapper = subscriptionMapper;
    }

    @Override
    public SubscriptionResponse subscribe(Long id, SubscriptionRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

        if (!plan.isActive()) {
            throw new BusinessRuleException("Cannot subscribe to inactive plan");
        }

        boolean alreadySubscribed = subscriptionRepository.existsByCustomerIdAndPlan_ServiceTypeAndStatus(
                id,
                plan.getServiceType(),
                SubscriptionStatus.ACTIVE
        );
        if (alreadySubscribed) {
            throw new BusinessRuleException("Customer already has an active subscription for this service type");
        }


        Subscription subscription = new Subscription();
        subscription.setCustomer(customer);
        subscription.setPlan(plan);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setCreatedAt(LocalDateTime.now());

        Subscription saved = subscriptionRepository.save(subscription);


        return subscriptionMapper.toResponse(saved);
    }

    @Override
    public SubscriptionResponse changePlan(Long subscriptionId, Long newPlanId) {

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        Plan newPlan = planRepository.findById(newPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
        if (!newPlan.isActive()) {
            throw new BusinessRuleException("Cannot switch to inactive plan");
        }
        if (!subscription.getPlan().getOperator().getId().equals(newPlan.getOperator().getId())) {
            throw new BusinessRuleException("Plan change must be with the same operator");
        }
        if (subscription.getPlan().getServiceType() != newPlan.getServiceType()) {
            throw new BusinessRuleException("Plan change must be with the same service type");
        }
        subscription.setPlan(newPlan);
        Subscription savedSubscription = subscriptionRepository.save(subscription);

        return subscriptionMapper.toResponse(savedSubscription);
    }

    @Override
    public void cancel(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        subscription.setStatus(SubscriptionStatus.CANCELED);
        subscription.setCancellationDate(LocalDateTime.now());


    }
}
