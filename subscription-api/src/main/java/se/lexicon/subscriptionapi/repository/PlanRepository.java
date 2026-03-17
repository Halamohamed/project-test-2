package se.lexicon.subscriptionapi.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.subscriptionapi.domain.entity.Plan;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByIsActiveTrue();
    List<Plan> findByOperatorIdAndIsActiveTrue(Long operatorId);

    boolean existsByNameAndOperatorId(@NotBlank String name, @NotNull Long operatorId);

    boolean existsByNameAndOperatorIdAndIdNot(@NotBlank String name, @NotNull Long operatorId, Long id);
}
