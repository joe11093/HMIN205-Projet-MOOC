package com.example.joseph.mooc.Models;

import java.io.Serializable;

/**
 * Created by josep on 4/28/2019.
 */

public class Login implements Serializable {
    private String email;
    private String password;


    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

