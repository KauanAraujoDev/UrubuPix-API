package com.example.demo.model;

public class Balance {
    private int Id;
    private String username;
    private int balance;

    public Balance(int Id, String username, int balance) {
        this.Id = Id;
        this.username = username;
        this.balance = balance;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}
