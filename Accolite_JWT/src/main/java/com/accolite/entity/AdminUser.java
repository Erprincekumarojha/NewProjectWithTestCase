package com.accolite.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADMIN_USER", schema = "ACCOLITE_USER")
public class AdminUser {

    @Id
    @GeneratedValue
    private Long Id;
    @Column(name = "ADMIN_USER_NAME")
    private String adminUserName;

    @Column(name = "ADMIN_PASSWORD")
    private String adminPassword;


    public String getAdminUserName() {
        return adminUserName;
    }


    public String getAdminPassword() {
        return adminPassword;
    }
}
