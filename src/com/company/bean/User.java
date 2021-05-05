package com.company.bean;

public class User {
    private int id;
    private String name;
    private String password;
    public User(){

    }
    public User(String name,String password){
        this.id=0;
        this.name= name;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
