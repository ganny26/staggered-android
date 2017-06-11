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

public class MyGridAdapter extends RecyclerView.Adapter  {

    private Context mContext;
    private List<ImageModel> imageModels = new ArrayList<>();

    public MyGridAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.resizable_grid_item, null);
        MyViewHolder holder = new MyViewHolder(itemView);
        holder.imageView = (DynamicHeightImageView) itemView.findViewById(R.id.dynamic_height_image_view);
        holder.positionTextView = (TextView) itemView.findViewById(R.id.item_position_view);
        itemView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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


//    private static class FooterViewHolder extends RecyclerView.ViewHolder{
//        private ProgressBar progressBar;
//
//        public ProgressBar getProgressBar() {
//            return progressBar;
//        }
//
//        public FooterViewHolder(View itemView) {
//            super(itemView);
//            this.progressBar = (ProgressBar) itemView.findViewById(R.id.main_progress);
//        }
//    }
}
