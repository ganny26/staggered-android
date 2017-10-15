package com.bacon.demo.adapter;

import java.util.List;

/**
 * Created by serajam on 10/14/2017.
 */

public class Department {
    public boolean mExpanded = false;
    public String name;
    private List<Department> mDepartments;

    public boolean ismExpanded() {
        return mExpanded;
    }

    public void setmExpanded(boolean mExpanded) {
        this.mExpanded = mExpanded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getmDepartments() {
        return mDepartments;
    }

    public void setmDepartments(List<Department> mDepartments) {
        this.mDepartments = mDepartments;
    }
}
