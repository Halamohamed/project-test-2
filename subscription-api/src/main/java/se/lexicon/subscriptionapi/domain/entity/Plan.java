package se.lexicon.subscriptionapi.domain.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "operator")
@Entity
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private double price;
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;
    private Integer dataLimit;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;


}
