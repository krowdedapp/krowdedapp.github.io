package com.example.thebestteam.cs495capstonecomputing;


import java.util.ArrayList;

/**
 * Created by Liam on 03/04/18.
 */



public class User {
    private String name;
    private String email;
    private int age;
    private int sex;
    private boolean isBusiness;
    private ArrayList<String> favs;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getSex() { return sex; }
    public void setSex(int sex) { this.sex = sex; }

    public boolean isBusiness() { return isBusiness; }
    public void setBusiness(boolean business) { isBusiness = business; }

    public ArrayList<String> getFavs() { return favs; }
    public void setFavs(ArrayList<String> favsNew) { favs = favsNew; }


    public User() {
        // function will not be used
    }

    public User(String name, String email, int age, int sex, Boolean isBiz, ArrayList<String> favs) {
        setName(name);
        setEmail(email);
        setAge(age);
        setSex(sex);
        setBusiness(isBiz);
        setFavs(favs);
    }
}
