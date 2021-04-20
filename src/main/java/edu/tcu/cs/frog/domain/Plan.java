package edu.tcu.cs.frog.domain;

import edu.tcu.cs.frog.service.UserService;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

}
