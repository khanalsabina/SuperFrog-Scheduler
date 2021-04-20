package edu.tcu.cs.frog.controller;

import edu.tcu.cs.frog.domain.MyUserPrincipal;
import edu.tcu.cs.frog.domain.Plan;
import edu.tcu.cs.frog.domain.User;
import edu.tcu.cs.frog.service.PlanService;
import edu.tcu.cs.frog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/frog")
public class PlanController {
    @Autowired
    private PlanService planService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String findAll(Model model) {
        List<Plan> plan = planService.findAll();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        User currentUser = principal.getUser();

        if (currentUser.getRoles().equals("admin")){
            model.addAttribute("plans", plan);
        }

        else if (currentUser.getRoles().equals("frog")){
            for(int i =0; i<plan.size(); i++)
            {
                if(!plan.get(i).getStatus().equals("Approve"))
                {
                    plan.remove(i);
                    i--;
                }
            }
            model.addAttribute("plans", plan);
        }
        else if (currentUser.getRoles().equals("user")){
            for(int i =0; i<plan.size(); i++)
            {
                if(!plan.get(i).getUser().getUsername().equals(currentUser.getUsername()))
                {
                    plan.remove(i);
                    i--;
                }
            }
            model.addAttribute("plans", plan);
        }
        return "frog/list";
    }
    @PostMapping()
    public String save(Plan newPlan, Model model) throws IOException {
        // save the product in DB
        if (newPlan.getUser() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
            newPlan.setUser(principal.getUser());
        }

        if (newPlan.getFrog() == null) {
            newPlan.setFrog(userService.findById(1));
        }
        Plan savePlan = planService.save(newPlan);
        return "redirect:/frog/list";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("plan", new Plan());
        return "frog/order_detail";
    }
//
    @GetMapping("/show/{id}")
    public String getProduct(@PathVariable Integer id, Model model){
        model.addAttribute("plan", planService.findById(id));
        return "frog/order";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("plan", planService.findById(id));
        List<User> userList = userService.findAll();

        for(int i =0; i<userList.size(); i++)
        {
            if(!userList.get(i).getRoles().equals("frog"))
            {
                userList.remove(i);
                i--;
            }
        }

        model.addAttribute("froglist", userList);
        return "frog/order_detail";
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        planService.delete(id);
        return "redirect:/frog/list";
    }
//    @GetMapping("/comments")
//    public String getComments(@RequestParam Integer prodId, Model model){
//        Product product = productService.findById(prodId);
//        model.addAttribute("product", product);
//        return "product/comments";
//    }
}
