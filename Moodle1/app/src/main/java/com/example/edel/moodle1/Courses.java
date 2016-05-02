package com.example.edel.moodle1;

/**
 * Created by Apple on 23/02/16.
 */
public class Courses {
    private String code;
    private String description;
    private String name;
    private String credits;
    private String format;
    private String  id;

    public Courses(String code, String desc, String name, String cred, String form, String id) {
        super();
        this.code = code;
        this.description = desc;
        this.name = name;
        this.credits = cred;
        this.format = form;
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
