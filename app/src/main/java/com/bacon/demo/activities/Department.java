package com.bacon.demo.activities;


import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;

import java.util.List;

public class Department implements ExpandableListItem {

    private boolean mExpand = false;
    public String name;
    public String displayTest;
    public List<Employee> mEmployees;

    public String getDisplayTest() {
        return displayTest;
    }

    public void setDisplayTest(String displayTest) {
        this.displayTest = displayTest;
    }

    @Override

    public List<?> getChildItemList() {
        return mEmployees;
    }

    @Override
    public boolean isExpanded() {
        return mExpand;
    }

    @Override
    public void setExpanded(boolean isExpanded) {
        mExpand = isExpanded;
    }

    @Override
    public String toString() {
        return "Department{" +
                "mExpand=" + mExpand +
                ", name='" + name + '\'' +
                ", mEmployees=" + mEmployees +
                '}';
    }
}
