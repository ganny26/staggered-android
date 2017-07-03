package com.bacon.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bacon.demo.R;
import com.bacon.demo.application.ImageModel;
import com.bacon.demo.utils.DynamicHeightImageView;
import com.bacon.demo.utils.PlaceHolderDrawableHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by serajam on 6/8/2017.
 */

public class MyGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean isFooterEnabled = true;

    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;





    private Context mContext;

    private List<ImageModel> imageModels = new ArrayList<>();

    public MyGridAdapter(Context mContext) {

        this.mContext = mContext;
    }
//
//    public MyGridAdapter (RecyclerView recyclerView){
//
//
//        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
//            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//
//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//
//                    totalItemCount = linearLayoutManager.getItemCount();
//                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//
//                        if (onLoadMoreListener != null) {
//                            onLoadMoreListener.onLoadMore();
//                        }
//                        loading = true;
//                    }
//                }
//            });
//        }
//    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(mContext).inflate(R.layout.resizable_grid_item, null);
//        MyViewHolder holder = new MyViewHolder(itemView);
//        holder.imageView = (DynamicHeightImageView) itemView.findViewById(R.id.dynamic_height_image_view);
//        holder.positionTextView = (TextView) itemView.findViewById(R.id.item_position_view);
//        itemView.setTag(holder);
//        return holder;
       // RecyclerView.ViewHolder recycleViewHolder;
       MyViewHolder recycleViewHolder;
        if(viewType == VIEW_ITEM){
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.resizable_grid_item, null);
            recycleViewHolder = new MyViewHolder(itemView);

            //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_progressbar_item, parent, false);
            //recycleViewHolder = new MyViewHolder(itemView);

            recycleViewHolder.imageView = (DynamicHeightImageView) itemView.findViewById(R.id.dynamic_height_image_view);
            recycleViewHolder.positionTextView = (TextView) itemView.findViewById(R.id.item_position_view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_progressbar_item, parent, false);
            recycleViewHolder = new ProgressViewHolderOutside(view);
            //recycleViewHolder = new ProgressViewHolderOutside(view);
        }
       // recycleViewHolder.imageView = (DynamicHeightImageView) itemView.findViewById(R.id.dynamic_height_image_view);
        //recycleViewHolder.positionTextView = (TextView) itemView.findViewById(R.id.item_position_view);
        //recycleViewHolder.setTag(holder);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            MyViewHolder vh = (MyViewHolder) holder;
            ImageModel item = imageModels.get(position);
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) vh.imageView.getLayoutParams();
            float ratio = item.getHeight() / item.getWidth();
            rlp.height = (int) (rlp.width * ratio);
            vh.imageView.setLayoutParams(rlp);
            vh.positionTextView.setText("pos: " + position);
            vh.imageView.setRatio(item.getRatio());
//        Picasso.with(mContext)
//                .load(item.getUrl())
//                .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
//                .into(vh.imageView);

            Glide.with(mContext)
                    .load(item.getUrl())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .crossFade()
                    .into(vh.imageView);


        }else{
            ((ProgressViewHolderOutside) holder).progressBar.setIndeterminate(true);
        }

    }




    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public void addDrawable(ImageModel imageModel) {
        float ratio = (float) imageModel.getHeight() / (float) imageModel.getWidth();
        imageModel.setRatio(ratio);
        this.imageModels.add(imageModel);
    }


    @Override
    public int getItemViewType(int position) {
        return imageModels.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void setLoaded() {
        loading = false;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.progressBar1);
        }
    }






}
