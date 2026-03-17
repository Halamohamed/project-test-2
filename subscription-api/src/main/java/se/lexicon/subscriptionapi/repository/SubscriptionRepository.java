package se.lexicon.subscriptionapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;
import se.lexicon.subscriptionapi.domain.entity.Customer;
import se.lexicon.subscriptionapi.domain.entity.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    Optional<Subscription> findByCustomerAndPlan_ServiceType(Customer customer, ServiceType serviceType);
    List<Subscription> findByCustomer(Customer customer);
    boolean existsByCustomerIdAndPlan_ServiceTypeAndStatus(Long customerId, ServiceType serviceType, SubscriptionStatus status);
}
