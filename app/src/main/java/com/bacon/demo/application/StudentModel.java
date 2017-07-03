package com.bacon.demo.application;

import java.io.Serializable;

/**
 * Created by serajam on 6/25/2017.
 */

public class StudentModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public StudentModel(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    private String name;

    private String emailId;
}
