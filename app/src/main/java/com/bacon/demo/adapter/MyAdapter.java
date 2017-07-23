package com.bacon.demo.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bacon.demo.R;
import com.bacon.demo.application.StudentModel;

import java.util.List;

/**
 * Created by serajam on 6/24/2017.
 */

public class MyAdapter  extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROGRESS = 0;

    private List<StudentModel> mDataset;

    // The minimum amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public MyAdapter(List<StudentModel> myDataSet, RecyclerView recyclerView) {
        mDataset = myDataSet;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) != null ? VIEW_ITEM : VIEW_PROGRESS;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
            vh = new StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_progressbar_item, parent, false);
            vh = new ProgressViewHolder1(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {

            StudentModel studentModel =  mDataset.get(position);

            ((StudentViewHolder) holder).tvName.setText(studentModel.getName());

            ((StudentViewHolder) holder).tvEmailId.setText(studentModel.getEmailId());

            ((StudentViewHolder) holder).student= studentModel;

        } else {
            ((ProgressViewHolder1) holder).progressBar.setIndeterminate(true);
        }
    }



    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public TextView tvEmailId;

        public StudentModel student;

        public StudentViewHolder(View v) {
            super(v);
//            tvName = (TextView) v.findViewById(R.id.tvName);
//
//            tvEmailId = (TextView) v.findViewById(R.id.tvEmailId);
//
//            v.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(),
//                            "OnClick :" + student.getName() + " \n "+student.getEmailId(),
//                            Toast.LENGTH_SHORT).show();
//
//                }
//            });
        }
    }



    public static class ProgressViewHolder1 extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder1(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }


}
