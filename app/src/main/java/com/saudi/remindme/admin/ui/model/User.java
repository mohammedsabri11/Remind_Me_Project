package com.saudi.remindme.admin.ui.model;

import java.io.Serializable;

public class User implements Serializable {

    private final String id;
    private final String name;
    private String dob;
    private String gender;
    private String phone;
    private boolean isActive;
    private String profile = null;

    public User(String id, String name) {
        this.id = id;
        this.name = name;

    }

    public User(String id, String name, String gender, boolean isActive) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.isActive = isActive;
        this.profile = null;

    }

    public User(String id, String name, String gender, String profile, boolean isActive) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.isActive = isActive;
        this.profile = profile;

    }

    public User(String id, String name, String dob, String gender, String phone) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
    }

    public User(String id, String name, String gender, String profile) {
        this.id = id;
        this.name = name;

        this.gender = gender;
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return name;
    }
}
