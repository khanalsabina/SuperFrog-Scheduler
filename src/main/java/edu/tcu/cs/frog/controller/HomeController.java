package edu.tcu.cs.frog.controller;

import edu.tcu.cs.frog.domain.MyUserPrincipal;
import edu.tcu.cs.frog.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request){
        model.addAttribute("today", Calendar.getInstance());

        // Access Currently Authenticated User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        request.getSession(true).setAttribute("user", principal.getUser());
        return "frog/home";
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
    public String signup(){

        return "frog/signup";
    }


}
