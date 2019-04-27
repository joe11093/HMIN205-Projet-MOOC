package com.example.joseph.mooc.Models;

import java.util.Date;

/**
 * Created by josep on 4/27/2019.
 */

public class Student extends User {

    private AnneeScolaire anneeScolaire;
    Parent parent;

    public Student() {
    }

    public Student(String firstname, String lastname, String dateofbirth, String email, String password, String city, String country, AnneeScolaire anneeScolaire, Parent parent) {
        super(firstname, lastname, dateofbirth, email, password, city, country, "student");
        this.anneeScolaire = anneeScolaire;
        this.parent = parent;
    }

    public Student(int id, String firstname, String lastname, String email, String password, String dateofbirth, String city, String country, AnneeScolaire anneeScolaire, Parent parent) {
        super(id, firstname, lastname, dateofbirth, email, password, city, country, "student");
        this.anneeScolaire = anneeScolaire;
        this.parent = parent;
    }


}