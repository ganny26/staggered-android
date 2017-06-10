package com.bacon.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bacon.demo.utils.DynamicHeightImageView;

/**
 * Created by serajam on 6/8/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public DynamicHeightImageView imageView;
    public TextView positionTextView;

    public MyViewHolder(View itemView) {
        super(itemView);
    }
}
