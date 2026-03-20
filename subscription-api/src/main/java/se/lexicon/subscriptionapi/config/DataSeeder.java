package se.lexicon.subscriptionapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.constant.Role;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Customer;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.repository.CustomerRepository;
import se.lexicon.subscriptionapi.repository.OperatorRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlanRepository planRepository;
    private final OperatorRepository operatorRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        seedAdminUser();
        seedRegularUser();
        seedPlanAndOperator();
    }

    private void seedAdminUser() {
        String adminEmail = "admin@example.com";
        if (!customerRepository.existsByEmail(adminEmail)) {
            Customer admin = new Customer();
            admin.setEmail(adminEmail);
            admin.setFirstName("Admin");
            admin.setLastName("Adminson");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
            customerRepository.save(admin);
            System.out.println("[DATA_SEED] Admin user created: " + adminEmail);
        }
    }

    private void seedRegularUser() {
        String userEmail = "user@example.com";
        if (!customerRepository.existsByEmail(userEmail)) {
            Customer user = new Customer();
            user.setEmail(userEmail);
            user.setFirstName("User");
            user.setLastName("Userson");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Set.of(Role.ROLE_USER));
            customerRepository.save(user);
            System.out.println("[DATA_SEED] Regular user created: " + userEmail);
        }
    }

    private void seedPlanAndOperator(){
        if (operatorRepository.count() > 0) {
            return;
        }

        Operator operator1 = new Operator();
        operator1.setName("Fiber");
        operator1.setCreatedAt(LocalDateTime.now());
        planRepository.save(new Plan("Fiber 50", 200.99, ServiceType.INTERNET,50, true,operator1));

    }

}
