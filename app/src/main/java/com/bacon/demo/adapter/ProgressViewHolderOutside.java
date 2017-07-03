package com.bacon.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bacon.demo.R;

/**
 * Created by serajam on 6/23/2017.
 */

public  class ProgressViewHolderOutside extends MyViewHolder {
    public ProgressBar progressBar;

    public ProgressViewHolderOutside(View v) {
        super(v);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
    }
}