package com.example.edel.moodle1;

/**
 * Created by Apple on 24/02/16.
 */
public class Notifications {

    private String user_id;
    private String description;
    private String is_seen;
    private String created;
    private String  id;

    public Notifications(String user_id, String description, String is_seen, String created, String id) {
        this.user_id = user_id;
        this.description = description;
        this.is_seen = is_seen;
        this.created = created;
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_seen() {
        return is_seen;
    }

    public void setIs_seen(String is_seen) {
        this.is_seen = is_seen;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
