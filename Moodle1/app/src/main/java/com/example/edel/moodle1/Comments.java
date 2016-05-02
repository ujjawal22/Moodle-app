package com.example.edel.moodle1;

/**
 * Created by Apple on 30/03/16.
 */
public class Comments {


    private String  comm_id;
    private String user_id;
    private String description;
    private String created;
    private String f_name;

    public Comments(String id, String user_id, String description, String created) {
        this.comm_id = id;
        this.user_id = user_id;
        this.description = description;
        this.created = created;

    }

    public String getId() {
        return comm_id;
    }

    public void setId(String id) {
        this.comm_id = id;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }


}
