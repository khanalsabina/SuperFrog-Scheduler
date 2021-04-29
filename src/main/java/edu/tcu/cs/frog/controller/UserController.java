package edu.tcu.cs.frog.controller;

import edu.tcu.cs.frog.dao.UserRepository;
import edu.tcu.cs.frog.domain.MyUserPrincipal;
import edu.tcu.cs.frog.domain.Plan;
import edu.tcu.cs.frog.domain.User;
import edu.tcu.cs.frog.service.PlanService;
import edu.tcu.cs.frog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/user")
    public String getCurrentUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        User currentUser = principal.getUser();
        model.addAttribute("user", currentUser);
        return "frog/user";
    }

    @GetMapping("/user/edit")
    public String editCurrentUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        User currentUser = principal.getUser();
        model.addAttribute("user", currentUser);
        return "frog/user_detail";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "frog/user_detail";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "frog/user";
    }

    @GetMapping("/user/list")
    public String getAllUser(Model model){
        List<User> userList = userService.findAll();
        model.addAttribute("user", userList);
        return "frog/user_list";
    }

    @PostMapping("/user/save")
    public String save(User newUser, Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        User currentUser = principal.getUser();

        User saveUser = userService.findById(newUser.getId());
        saveUser.setRoles(newUser.getRoles());
        saveUser.setEmail(newUser.getEmail());
        saveUser.setFirstname(newUser.getFirstname());
        saveUser.setLastname(newUser.getLastname());
        if (currentUser.getRoles().equals("admin")) saveUser.setEnabled(newUser.isEnabled());
        userRepository.save(saveUser);

        //update the current session user
        if (currentUser.getId() == saveUser.getId()) {
            currentUser.setRoles(newUser.getRoles());
            currentUser.setEmail(newUser.getEmail());
            currentUser.setFirstname(newUser.getFirstname());
            currentUser.setLastname(newUser.getLastname());
        }
        if (currentUser.getRoles().equals("admin")) return "redirect:/user/list";
        return "redirect:/user";
    }


}