package edu.tcu.cs.frog.controller;

import edu.tcu.cs.frog.domain.MyUserPrincipal;
import edu.tcu.cs.frog.domain.User;
import edu.tcu.cs.frog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/home")
    public String home(Model model, HttpServletRequest request){
        model.addAttribute("today", Calendar.getInstance());

        // Access Currently Authenticated User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        request.getSession(true).setAttribute("user", principal.getUser());
        return "frog/home";
    }

    @GetMapping("/")
    public String home(){
        return "frog/home_nologin";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "frog/login";
    }
    @GetMapping("/access_denied")
    String accessDenied(){
        return "accessDenied";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("user", new User());
        return "frog/signup";
    }

    @PostMapping("/signup/success")
    public String processRegister(User user) {
        userService.save(new User(
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                true,
                "user",
                user.getNationality(),
                user.getAge()
        ));

        return "frog/success";
    }
}
