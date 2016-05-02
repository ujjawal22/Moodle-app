package com.example.edel.moodle1;

/**
 * Created by Apple on 27/03/16.
 */
public class Assignment {
    private String name;
    private String description;
    private String created;
    private String deadline;
    private String  id;
    private String late;

    public Assignment(String name, String id, String deadline, String description,String created,String late) {
        this.name = name;
        this.id = id;
        this.deadline = deadline;
        this.description = description;
        this.created = created;
        this.late = late;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }
}
