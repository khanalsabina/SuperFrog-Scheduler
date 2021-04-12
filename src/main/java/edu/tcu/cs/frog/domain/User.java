package edu.tcu.cs.frog.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;

    private String firstname;
    private String lastname;
    private boolean enabled;
    private String roles;
    private String nationality;
    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Plan> planGive = new ArrayList<Plan>();

    @OneToMany(mappedBy = "frog", cascade = CascadeType.ALL)
    private List<Plan> planTake = new ArrayList<Plan>();

    public User(String username, String password, String firstname, String lastname, boolean enabled, String roles, String nationality, Integer age) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.enabled = enabled;
        this.roles = roles;
        this.nationality = nationality;
        this.age = age;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Plan> getPlanGive() {
        return planGive;
    }

    public void setPlanGive(List<Plan> planGive) {
        this.planGive = planGive;
    }
    public void addPlanGive(Plan plan){
        this.planGive.add(plan);
        plan.setUser(this);
    }

    public void setPlanTake(List<Plan> planTake) {
        this.planTake = planTake;
    }
    public void addPlanTake(Plan plan){
        this.planTake.add(plan);
        plan.setFrog(this);
    }
}
