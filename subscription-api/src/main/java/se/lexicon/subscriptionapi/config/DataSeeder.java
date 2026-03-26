package se.lexicon.subscriptionapi.config;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.constant.Role;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;
import se.lexicon.subscriptionapi.domain.entity.*;
import se.lexicon.subscriptionapi.repository.CustomerRepository;
import se.lexicon.subscriptionapi.repository.OperatorRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.repository.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlanRepository planRepository;
    private final OperatorRepository operatorRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        seedAdminUser();
        seedRegularUser();
        //seedCustomer();
        //seedPlanAndOperator();
        //seedSubscription();

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

        Operator fiberNet = new Operator();
        fiberNet.setName("Fiber");
        fiberNet.setCreatedAt(LocalDateTime.now());
        planRepository.save(new Plan("Fiber 50", 200.99, ServiceType.INTERNET,50, true,fiberNet));
        planRepository.save(new Plan("Fiber 100", 300.99, ServiceType.INTERNET,100, false,fiberNet));
        planRepository.save(new Plan("Fiber 300",400.99, ServiceType.INTERNET,300, false,fiberNet));

        Operator mobileNet = new Operator();
        mobileNet.setName("MobileOne");
        mobileNet.setCreatedAt(LocalDateTime.now());
        planRepository.save(new Plan("Mobile Basic", 29.99, ServiceType.INTERNET,5, true,mobileNet));
        planRepository.save(new Plan("Mobile Plus", 49.99, ServiceType.INTERNET,20, false,mobileNet));
        planRepository.save(new Plan("Mobile Unlimited", 99.95, ServiceType.INTERNET,20, false,mobileNet));


        IO.println("[DATA_SEED] Plan and Operator created: " + mobileNet.getName());
    }
    private void seedCustomer(){
        String email1 = "alina@example.com";
        if (!customerRepository.existsByEmail(email1)) {
            Customer alina = new Customer();
            alina.setEmail("alina@example.com");
            alina.setFirstName("Alina");
            alina.setLastName("Alinson");
            alina.setPassword(passwordEncoder.encode("password"));
            alina.setRoles(Set.of(Role.ROLE_USER));
            customerRepository.save(alina);
            IO.println("[DATA_SEED] Customer created: " + email1);
        }
        String email2 = "talia@ecample.com";
        if (!customerRepository.existsByEmail(email2)) {
            Customer tali = new Customer();
            tali.setEmail("talia@example.com");
            tali.setFirstName("Talia");
            tali.setLastName("Talisson");
            tali.setPassword(passwordEncoder.encode("password"));
            tali.setRoles(Set.of(Role.ROLE_USER));
            customerRepository.save(tali);
            IO.println("[DATA_SEED] Customer created: " + email2);
        }
        CustomerDetail taliDetail = new CustomerDetail();
        Customer tali = customerRepository.findByEmail(email2).orElseThrow();
        taliDetail.setCustomer(tali);
        taliDetail.setPhoneNumber("0721 xxx xx");



    }

    private void seedSubscription(){
        Customer customer1 = customerRepository.findByEmail("alina@example.com").orElse(null);
        Customer customer2 = customerRepository.findByEmail("talia@example.com").orElseThrow();

        Plan fiber100 = planRepository.findByName("Fiber 100").orElseThrow();
        Plan mobilePlus = planRepository.findByName("Mobile Plus").orElseThrow();

        Subscription subscription1 = new Subscription();
        subscription1.setCustomer(customer1);
        subscription1.setPlan(fiber100);
        subscription1.setStatus(SubscriptionStatus.ACTIVE);
        subscriptionRepository.save(subscription1);

        Subscription subscription2 = new Subscription();
        subscription2.setCustomer(customer2);
        subscription2.setPlan(mobilePlus);
        subscription2.setStatus(SubscriptionStatus.ACTIVE);
        subscriptionRepository.save(subscription2);

        IO.println("[DATA_SEED] two customers subscribed: " + subscription1.getCustomer().getEmail() + " and " + subscription2.getCustomer().getEmail());


    }




}
