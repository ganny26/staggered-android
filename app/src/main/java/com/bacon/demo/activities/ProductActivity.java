package com.bacon.demo.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bacon.demo.R;
import com.bacon.demo.adapter.Department;
import com.bacon.demo.adapter.DepartmentItem;
import com.bacon.demo.adapter.Helper;
import com.bacon.demo.adapter.HelperItem;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import java.util.ArrayList;
import java.util.List;


public class ProductActivity extends AppCompatActivity implements View.OnClickListener {


    private FloatingActionButton fab_help_btn;
    private Context mContext;
    private Boolean wrapInScrollView = true;
    private Button togglebtn;
    private RecyclerView mRecyclerView;
    private BaseExpandableAdapter mBaseExpandableAdapter;
    private boolean hasAdd = false;
    private List mHelpList;
    private final int ITEM_TYPE_COMPANY = 1;
    private final int ITEM_TYPE_DEPARTMENT = 2;

    MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carb);
        fab_help_btn = (FloatingActionButton) findViewById(R.id.fab_help);
        fab_help_btn.setOnClickListener(this);
        initData();
        dialog = new MaterialDialog.Builder(this)
                .title("Adapter")
                .customView(R.layout.fab_layout, wrapInScrollView).build();
        mRecyclerView = (RecyclerView) dialog.findViewById(R.id.recycle_view);

        setUpAdapter();
    }

    public void setUpAdapter(){
        mBaseExpandableAdapter = new BaseExpandableAdapter(mHelpList) {
            @NonNull
            @Override
            public AbstractAdapterItem<Object> getItemView(Object type) {
                int itemType = (int) type;
                switch (itemType) {
                    case ITEM_TYPE_COMPANY:
                        return new HelperItem();
                    case ITEM_TYPE_DEPARTMENT:
                        return new DepartmentItem();
                }
                return null;
            }

            @Override
            public Object getItemViewType(Object t) {
                if (t instanceof Helper) {
                    return ITEM_TYPE_COMPANY;
                }else if (t instanceof Department)
                    return ITEM_TYPE_DEPARTMENT;
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


    public void initData() {
        mHelpList = new ArrayList<>();

        mHelpList.add(createCompany("Google", true));

        mHelpList.add(createCompany("Apple", true));
    }


    public Helper createCompany(String companyName, boolean isExpandDefault) {
        Helper firstCompany = new Helper();
        firstCompany.name = companyName;
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Department department = new Department();
            department.name = "Department:" + i;
            departments.add(department);
        }
        firstCompany.mDepartments = departments;
        firstCompany.mExpanded = isExpandDefault;
        return firstCompany;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_help:
                dialog.show();
                break;
        }
    }
}
