package se.lexicon.subscriptionapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
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

    @Override
    public void run(String... args) throws Exception {
        seedAdminUser();
        seedRegularUser();
        seedOperator();
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

    private void seedOperator(){
        if (operatorRepository.count() > 0) {
            return;
        }
        Operator operator1 = new Operator();
        operator1.setName("Fiber");
        operator1.setCreatedAt(LocalDateTime.now());
        operator1.setPlans(List.of(new Plan(1L,"Fiber 50", 200.99, ServiceType.INTERNET,50, true,operator1),
                new Plan(2L, "Fiber 100", 390.99,  ServiceType.INTERNET,100, true,operator1),
                new Plan(3L, "Fiber 300", 590.99, ServiceType.INTERNET, 300, false, operator1)));
        Operator operator2 = new Operator();
        operator2.setName("Mobile");
        operator2.setCreatedAt(LocalDateTime.now());
        operator2.setPlans(List.of(new Plan(4L, "Mobile Basic", 190.99,  ServiceType.MOBILE, 5, true, operator2),
                new Plan(5L, "Mobile Plus", 290.99,   ServiceType.MOBILE, 20, true, operator2),
                new Plan(6L, "Mobile Unlimited", 490.99, ServiceType.MOBILE, null, false, operator2)));

        operatorRepository.save(operator1);
        operatorRepository.save(operator2);

        // planRepository.save(new Plan(5L,"Fiber 50", 200.99, ServiceType.INTERNET,50, true,operator1));
       /* planRepository.save(new Plan(2L, "Fiber 100", 390.99,  ServiceType.INTERNET,100, true,operator1));
        planRepository.save(new Plan(3L, "Fiber 300", 590.99, ServiceType.INTERNET, 300, false, operator1));
*/
       // planRepository.save(new Plan(4L, "Mobile Basic", 190.99,  ServiceType.MOBILE, 5, true, operator2));
      /*  planRepository.save(new Plan(5L, "Mobile Plus", 290.99,   ServiceType.MOBILE, 20, true, operator2));
        planRepository.save(new Plan(6L, "Mobile Unlimited", 490.99, ServiceType.MOBILE, null, false, operator2));
   */ }

}
