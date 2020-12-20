package com.example.gallerydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyImageAdapter extends RecyclerView.Adapter <MyImageAdapter.ViewHolder> {

    private List<MyImage> mImageList = new ArrayList<>();
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    /**
     * 自定义RecyclerView 中item view点击回调方法
     */
    interface OnRecyclerItemClickListener{
        /**
         * item view 回调方法
         * @param view  被点击的view
         * @param position 点击索引
         */
        void onItemClick(View view, int position);
    }


    public MyImageAdapter(Context context, List<MyImage> myImages, OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.mImageList = myImages;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_album, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(view, (int) view.getTag());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyImage myImage = mImageList.get(position);
        holder.itemView.setTag(position);
        holder.imageView.setImageResource(myImage.getImageeId());

    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView = null;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.album);
        }
    }
}
