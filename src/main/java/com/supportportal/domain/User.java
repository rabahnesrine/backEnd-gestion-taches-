package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supportportal.constant.Authority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
   // @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String userId;
    private String username;
    private String  telephone;
    private  String professionUser;
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;
    private String role; //ROLE_USER{ read, edit }, ROLE_ADMIN {delete}
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;
    @OneToMany(mappedBy ="creePar" )
    private List<Projet> projets;
    @OneToMany(mappedBy ="sprintCreePar" )
    private List<Sprint> sprints;
    @OneToMany(mappedBy ="chefAffecter" )
    private List<Sprint> sprintsAffecter;

    @OneToMany( mappedBy = "memberAffecter")
    private List<Task> tasksMember;

    @OneToMany( mappedBy = "taskCreePar")
    private List<Task> tasksChef;

    @OneToMany( mappedBy = "userCom")
    private List<Commentaire> userCom;



    public User(){}

    public User(Long id, String userId, String telephone,  String professionUser,
            String username, String password, String email, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String role, String[] authorities, boolean isActive, boolean isNotLocked, List<Projet> projets, List<Sprint> sprints, List<Sprint> sprintsAffecter ,List<Task> tasksMember ,List<Task> tasksChef,List<Commentaire> userCom) {
        this.id = id;
        this.userId = userId;
    this.telephone=telephone;
        this.username = username;
        this.password = password;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.joinDate = joinDate;
        this.role = role;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
        this.professionUser=professionUser;
        this.projets = projets;
        this.sprints=sprints;
        this.sprintsAffecter=sprintsAffecter;
        this.tasksChef=tasksChef;
        this.tasksMember=tasksMember;
        this.userCom=userCom;

    }

    public List<Task> getTasksMember() {
        return null;
    }

    public void setTasksMember(List<Task> tasksMember) {
        this.tasksMember = tasksMember;
    }

    public List<Task> getTasksChef() {
        return null;
    }

    public void setTasksChef(List<Task> tasksChef) {
        this.tasksChef = tasksChef;
    }

    public List<Commentaire> getUserCom() {
        return null;
    }

    public void setUserCom(List<Commentaire> userCom) {
        this.userCom = userCom;
    }

    public List<Sprint> getSprintsAffecter() {
        return null;
    }

    public void setSprintsAffecter(List<Sprint> sprintsAffecter) {
        this.sprintsAffecter = sprintsAffecter;
    }

    public List<Sprint> getSprints() {
        return null;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    public List<Projet> getProjets() {
        return null;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

    public String getProfessionUser() {
        return professionUser;
    }

    public void setProfessionUser(String professionUser) {
        this.professionUser = professionUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsername() {
        return this.username;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLoginDateDisplay() {
        return lastLoginDateDisplay;
    }

    public void setLastLoginDateDisplay(Date lastLoginDateDisplay) {
        this.lastLoginDateDisplay = lastLoginDateDisplay;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }

    public boolean getIsNotLocked() {
        return this.isNotLocked;
    }

    public void setIsNotLocked(boolean notLocked) {
        this.isNotLocked = notLocked;
    }
}
