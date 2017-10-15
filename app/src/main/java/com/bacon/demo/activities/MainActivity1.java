package com.bacon.demo.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bacon.demo.R;
import com.bacon.demo.activities.CompanyItem;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener {

    private final int ITEM_TYPE_COMPANY = 1;
    private final int ITEM_TYPE_DEPARTMENT = 2;
    private final int ITEM_TYPE_EMPLOYEE = 3;

    private RecyclerView mRecyclerView;
    private BaseExpandableAdapter mBaseExpandableAdapter;
    private List mCompanylist;
    private Boolean wrapInScrollView = true;
    private boolean hasAdd = false;
    private FloatingActionButton fab_help;
    private MaterialDialog dialog;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // mRecyclerView = (RecyclerView) findViewById(R.id.rcv);
        fab_help = (FloatingActionButton) findViewById(R.id.fab_help);
        fab_help.setOnClickListener(this);

        initData();
        dialog = new MaterialDialog.Builder(this)
                .title("Frequently asked questions")
                .customView(R.layout.fab_layout, wrapInScrollView).build();
       mRecyclerView = (RecyclerView) dialog.findViewById(R.id.recycle_view);

        mBaseExpandableAdapter = new BaseExpandableAdapter(mCompanylist) {
            @NonNull
            @Override
            public AbstractAdapterItem<Object> getItemView(Object type) {
                int itemType = (int) type;
                switch (itemType) {
                    case ITEM_TYPE_COMPANY:
                        return new CompanyItem();
                    case ITEM_TYPE_DEPARTMENT:
                        return new DepartmentItem();
                    case ITEM_TYPE_EMPLOYEE:
                        return new EmployeeItem();
                }
                return null;
            }

            @Override
            public Object getItemViewType(Object t) {
                if (t instanceof Company) {
                    return ITEM_TYPE_COMPANY;
                } else if (t instanceof Department)
                    return ITEM_TYPE_DEPARTMENT;
                else if (t instanceof Employee)
                    return ITEM_TYPE_EMPLOYEE;
                return -1;
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setAdapter(mBaseExpandableAdapter);

        mBaseExpandableAdapter.setExpandCollapseListener(new BaseExpandableAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {

            }

            @Override
            public void onListItemCollapsed(int position) {

            }
        });
    }

    private void initData() {
        mCompanylist = new ArrayList<>();

        mCompanylist.add(createCompany("What is sematic search?", true));

        mCompanylist.add(createCompany("How to search recipes?", true));

        mCompanylist.add(createCompany("What is strict mode?", false));
    }

    @NonNull
    private Company createCompany(String companyName, boolean isExpandDefault) {
        Company firstCompany = new Company();
        firstCompany.name = companyName;
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Department department = new Department();
            department.name = "Department:" + i;
            if (i == 0) {
                department.setExpanded(true);
                List<Employee> employeeList = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    Employee employee = new Employee();
                    employee.name = "Employee:" + j;
                    employeeList.add(employee);
                }
                department.mEmployees = employeeList;
            }
            departments.add(department);
        }
        firstCompany.mDepartments = departments;
        firstCompany.mExpanded = isExpandDefault;
        return firstCompany;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.fab_help) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_help:
                Log.i("TAG","Clicked");
                dialog.show();
                break;
        }
    }
}
