package com.example.edel.moodle1;



/**
 * Created by Apple on 30/03/16.
 */
public class Users {

    private String id;
    private String f_name;

    public Users(String id, String f_name) {
        this.id = id;
        this.f_name = f_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }
}
