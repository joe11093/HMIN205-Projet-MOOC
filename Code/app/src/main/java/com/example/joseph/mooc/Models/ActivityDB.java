package com.example.joseph.mooc.Models;

/**
 * Created by josep on 5/26/2019.
 */

public class ActivityDB {
    String id;
    String user_id;
    String type;
    String ref;
    String date;
    String time;
    String activite_text;

    public ActivityDB(String user_id, String type, String ref, String activite_text) {
        this.user_id = user_id;
        this.type = type;
        this.ref = ref;
        this.activite_text = activite_text;
    }

    public ActivityDB(String id, String user_id, String type, String ref, String activite_text) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.ref = ref;
        this.activite_text = activite_text;
    }


    public ActivityDB(String id, String user_id, String type, String ref, String date, String time, String activite_text) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.ref = ref;
        this.date = date;
        this.time = time;
        this.activite_text = activite_text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActivite_text() {
        return activite_text;
    }

    public void setActivite_text(String activite_text) {
        this.activite_text = activite_text;
    }
}
