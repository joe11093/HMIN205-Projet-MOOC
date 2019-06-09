package com.example.joseph.mooc.Models;

import java.util.Date;

/**
 * Created by josep on 4/27/2019.
 */

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String dateofbirth;
    private String emailaddress;
    private String password;
    private String city;
    private String country;
    private String type;

    //default contructor
    public User() {

    }

    //to return from the login async activity
    public User(int id, String firstname, String emailaddress, String type) {
        this.id = id;
        this.firstname = firstname;
        this.emailaddress = emailaddress;
        this.type = type;
    }

    //constructor with ID
    public User(int id, String firstname, String lastname, String dateofbirth, String emailaddress, String password, String city, String country, String type) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateofbirth = dateofbirth;
        this.emailaddress = emailaddress;
        this.password = password;
        this.city = city;
        this.country = country;
        this.type = type;
    }

    //constructor without id
    public User(String firstname, String lastname, String dateofbirth, String emailaddress, String password, String city, String country, String type) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateofbirth = dateofbirth;
        this.emailaddress = emailaddress;
        this.password = password;
        this.city = city;
        this.country = country;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}