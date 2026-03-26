package se.lexicon.subscriptionapi.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByIsActiveTrue();
    List<Plan> findByOperatorIdAndIsActiveTrue(Long operatorId);
    Optional<Plan> findByName(String name);

    boolean existsByNameAndOperatorId(@NotBlank String name, @NotNull Long operatorId);

    boolean existsByNameAndOperatorIdAndIdNot(@NotBlank String name, @NotNull Long operatorId, Long id);

    List<Plan> findByIsActiveTrueAndServiceType(@NotNull ServiceType serviceType);
}
