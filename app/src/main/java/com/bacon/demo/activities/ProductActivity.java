package com.bacon.demo.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;

import android.widget.ListView;


import com.bacon.demo.R;
import com.bacon.demo.adapter.MyAdapter;

import com.bacon.demo.application.StudentModel;

import java.util.ArrayList;


public class ProductActivity extends AppCompatActivity {

    private static final String TAG = "ProductActivity";


    private ListView productListView;
    private MyAdapter myAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<StudentModel> myDataset;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



    }



}
