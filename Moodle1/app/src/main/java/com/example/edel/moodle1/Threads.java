package com.example.edel.moodle1;

/**
 * Created by Apple on 27/03/16.
 */
public class Threads {

    private String title;
    private String description;
    private String created;
    private String updated;
    private String  id;
    private String user_id;

    public Threads(String title, String description, String created, String updated, String id, String user_id) {
        this.title = title;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.id = id;
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
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
}
