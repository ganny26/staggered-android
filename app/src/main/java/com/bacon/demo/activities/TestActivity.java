package com.bacon.demo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bacon.demo.R;
import com.lapism.searchview.SearchView;

public class TestActivity extends AppCompatActivity {

    private SearchView mSearchView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

       mSearchView = (SearchView) findViewById(R.id.searchView);
    }
}
