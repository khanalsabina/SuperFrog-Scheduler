package edu.tcu.cs.frog.controller;

import edu.tcu.cs.frog.domain.Comment;
import edu.tcu.cs.frog.domain.MyUserPrincipal;
import edu.tcu.cs.frog.domain.Plan;
import edu.tcu.cs.frog.domain.User;
import edu.tcu.cs.frog.service.PlanExcelExporter;
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


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @GetMapping("/export")
    public void export( HttpServletResponse response,
                        @RequestParam(value = "start", required = false) String start,
                        @RequestParam(value = "end", required = false) String end) throws IOException {
        List<Plan> planList = planService.findAll();
        System.out.println(start);
        System.out.println(end);
        for (int i =0; i<planList.size(); i++){
            Plan plan = planList.get(i);
            if (!plan.checkStart(start) || !plan.checkEnd(end)) {
                planList.remove(i);
                i--;
            }
        }
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        PlanExcelExporter excelExporter = new PlanExcelExporter(planList);

        excelExporter.export(response);
    }

    @GetMapping("/export/all")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Plan> plan = planService.findAll();

        PlanExcelExporter excelExporter = new PlanExcelExporter(plan);

        excelExporter.export(response);
    }
    @GetMapping("/comments")
    public String getComments(@RequestParam Integer prodId, Model model){
        Plan plan = planService.findById(prodId);
        model.addAttribute("plan", plan);
        return "frog/comments";
    }

    @GetMapping("/comments/new/{id}")
    public String newComments(@PathVariable Integer id, Model model){
        Plan plan = planService.findById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        User currUser = principal.getUser();
        String title = "";
        if (currUser.getRoles().equals("frog"))
            title = currUser.getUsername() + ": volunteer for this event.";
        else if (currUser.getRoles().equals("user"))
            title = currUser.getUsername() + ": want to change this event's detail.";

        Comment comment = new Comment(title,"");
        model.addAttribute("plan",plan);
        model.addAttribute("comment", comment);

        return "frog/new_comments";
    }

    @PostMapping("/save/{id}")
    public String save(@PathVariable Integer id, Comment newComment, Model model) throws IOException {
        Plan plan = planService.findById(id);
        plan.addComment(newComment);
        Plan savePlan = planService.save(plan);
        return "redirect:/frog/list";
    }
}
