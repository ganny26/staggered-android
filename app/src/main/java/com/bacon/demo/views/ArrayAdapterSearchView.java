package com.bacon.demo.views;

import android.content.Context;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bacon.demo.R;

/**
 * Created by serajam on 6/13/2017.
 */

public class ArrayAdapterSearchView extends SearchView {

    private SearchView.SearchAutoComplete mSearchAutoComplete;

    public ArrayAdapterSearchView(Context context) {
        super(context);
        initialize();

    }

    @Override
    public void setSuggestionsAdapter(CursorAdapter adapter) {
        super.setSuggestionsAdapter(adapter);
    }

    public ArrayAdapterSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();

    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mSearchAutoComplete.setOnItemClickListener(listener);
    }

    public void setAdapter(ArrayAdapter<?> adapter) {
        mSearchAutoComplete.setAdapter(adapter);
    }

    public void initialize(){
        mSearchAutoComplete = (SearchAutoComplete) findViewById(R.id.menuSearch);
        this.setAdapter(null);
        this.setOnItemClickListener(null);
    }

    public void setText(String text) {
        mSearchAutoComplete.setText(text);
    }



}
