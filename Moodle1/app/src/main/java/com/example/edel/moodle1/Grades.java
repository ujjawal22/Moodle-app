package com.example.edel.moodle1;

/**
 * Created by Apple on 24/02/16.
 */
public class Grades {

    private String code;
    private String description;
    private String name;
    private String credits;
    private String format;
    private String  id;

    private String weight;
    private String user_id;
    private String ass_name;
    private String total;
    private String course_id;
    private String score;
    private String grade_id;

    public Grades(String code, String weight, String user_id, String ass_name, String total, String course_id, String score, String grade_id) {
        this.code = code;
        this.weight = weight;
        this.user_id = user_id;
        this.ass_name = ass_name;
        this.total = total;
        this.course_id = course_id;
        this.score = score;
        this.grade_id = grade_id;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAss_name() {
        return ass_name;
    }

    public void setAss_name(String ass_name) {
        this.ass_name = ass_name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }
}
