package com.example.joseph.mooc.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by josep on 4/27/2019.
 */

public class Parent extends User implements Serializable {


    public Parent() {
    }

    public Parent(String firstname, String lastname, String dateofbirth, String email, String password, String city, String country) {
        super(firstname, lastname, dateofbirth, email, password, city, country, "parent");
    }


    public Parent(int id, String firstname, String lastname, String dateofbirth, String email, String password, String city, String country) {
        super(id, firstname, lastname, dateofbirth, email, password, city, country, "parent");
    }


}
