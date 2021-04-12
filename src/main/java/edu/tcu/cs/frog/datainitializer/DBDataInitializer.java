package edu.tcu.cs.frog.datainitializer;


import edu.tcu.cs.frog.domain.Plan;
import edu.tcu.cs.frog.domain.User;
import edu.tcu.cs.frog.service.PlanService;
import edu.tcu.cs.frog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DBDataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
    private void loadData() {

        // create some users
        User u1 = new User();
        u1.setUsername("user");
        u1.setPassword("123456");
        u1.setEnabled(true);
        u1.setRoles("user");
        u1.setFirstname("John");
        u1.setLastname("Apricot");

        User u2 = new User();
        u2.setUsername("admin");
        u2.setPassword("123456");
        u2.setEnabled(true);
        u2.setRoles("admin");

        User u3 = new User();
        u3.setUsername("frog");
        u3.setPassword("123456");
        u3.setEnabled(true);
        u3.setRoles("frog");

        userService.save(u1);
        userService.save(u2);
        userService.save(u3);

        //create some plan
        Plan p1 = new Plan("Approve");
        Plan p2 = new Plan("Reject");
        Plan p3 = new Plan("Pending");

        u1.addPlanGive(p1);
        u3.addPlanTake(p1);

        u1.addPlanGive(p2);
        u1.addPlanGive(p3);

        planService.save(p1);
        planService.save(p2);
        planService.save(p3);
    }
}
