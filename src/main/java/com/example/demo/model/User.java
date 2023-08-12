package com.example.demo.model;

import jakarta.annotation.Generated;

import java.sql.Date;
import java.time.Instant;

public class User {
    private int id;
    private String username;
    private int age;
    private String plan;
    private String timestamp;

    public User(int id, String username, int age, String plan, String timestamp) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.plan = plan;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", plan='" + plan + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}