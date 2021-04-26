package edu.tcu.cs.frog.domain;

import edu.tcu.cs.frog.service.UserService;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1000)
    private String description = null;

    private String status = "Pending";

    private String time = null;

    private String location = null;

    private String theme = null;

    @ManyToOne
    private User user = null;

    @ManyToOne
    private User frog = null;

    public Plan() {
        this.user = null;
    }

    public Plan(String status) {
        this.status = status;

    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String status) {
        this.theme = theme;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {return user;}

    public void setUser(User user) { this.user = user;}

    public User getFrog() {return frog;}

    public void setFrog(User frog) {this.frog = frog;}

    public boolean checkStart(String start){
        if (this.getTime() == null) return false;
        StringTokenizer st = new StringTokenizer(start,"/");
        int startMonth = Integer.parseInt(st.nextToken());
        int startDate = Integer.parseInt(st.nextToken());
        int startYear = Integer.parseInt(st.nextToken());

        StringTokenizer st2 = new StringTokenizer(this.getTime(),"/");
        int month = Integer.parseInt(st2.nextToken());
        int date = Integer.parseInt(st2.nextToken());
        int year = Integer.parseInt(st2.nextToken());


        if (year > startYear) return true;
        else if (year == startYear){
            if (month > startMonth) return true;
            else if (month == startMonth){
                if (date >= startDate) return true;
            }
        }
        return false;
    }

    public boolean checkEnd(String end){
        if (this.getTime() == null) return false;
        StringTokenizer st = new StringTokenizer(end,"/");
        int endMonth = Integer.parseInt(st.nextToken());
        int endDate = Integer.parseInt(st.nextToken());
        int endYear = Integer.parseInt(st.nextToken());

        StringTokenizer st2 = new StringTokenizer(this.getTime(),"/");
        int month = Integer.parseInt(st2.nextToken());
        int date = Integer.parseInt(st2.nextToken());
        int year = Integer.parseInt(st2.nextToken());

        if (year < endYear) return true;
        else if (year == endYear){
            if (month < endMonth) return true;
            else if (month == endMonth){
                if (date <= endDate) return true;
            }
        }
        return false;
    }

}
