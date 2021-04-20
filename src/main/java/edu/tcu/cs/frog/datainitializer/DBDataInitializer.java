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
        User u0 = new User(); //u0 is the default for not having a superfrog yet.
        u0.setEnabled(true);
        u0.setRoles("frog");
        u0.setUsername("NoFrog");
        u0.setPassword("123456");

        User u1 = new User();
        u1.setUsername("user");
        u1.setPassword("123456");
        u1.setEnabled(true);
        u1.setRoles("user");
        u1.setFirstname("John");
        u1.setLastname("Apricot");
        u1.setEmail("fake@gmail.com");

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

        User u4 = new User();
        u4.setUsername("frog4");
        u4.setPassword("123456");
        u4.setEnabled(true);
        u4.setRoles("frog");

        User u5 = new User();
        u5.setUsername("superfrog");
        u5.setPassword("123456");
        u5.setEnabled(true);
        u5.setRoles("frog");

        User u6 = new User();
        u6.setUsername("user2");
        u6.setPassword("123456");
        u6.setEnabled(true);
        u6.setRoles("user");
        u6.setEmail("fake2@gmail.com");

        userService.save(u0);
        userService.save(u1);
        userService.save(u2);
        userService.save(u3);
        userService.save(u4);
        userService.save(u5);
        userService.save(u6);

        //create some plan
        Plan p1 = new Plan("Approve");
        Plan p2 = new Plan("Reject");
        Plan p3 = new Plan("Approve");
        Plan p4 = new Plan("Pending");

        p1.setTime("5/1/2021");
        p2.setTime("5/2/2021");
        p3.setTime("5/3/2021");

        p1.setLocation("FortWorth");
        p2.setLocation("Houston");
        p3.setLocation("TexasCity");

        p1.setUser(u1);
        p2.setUser(u6);
        p3.setUser(u1);
        p4.setUser(u2);

        p1.setFrog(u3);
        p2.setFrog(u0);
        p3.setFrog(u4);
        p4.setFrog(u0);

        planService.save(p1);
        planService.save(p2);
        planService.save(p3);
        planService.save(p4);
    }
}
