package com.bacon.demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bacon.demo.application.Product;
import com.bacon.demo.application.StudentModel;

import java.util.List;

/**
 * Created by serajam on 6/25/2017.
 */

public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private List<Product> mProductList;

    public ProductListAdapter(Context context, List<Product> mProductList) {
        this.context = context;
        this.mProductList = mProductList;
    }

    public void addListItemToAdapter(List<Product> productList){
        mProductList.addAll(productList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
