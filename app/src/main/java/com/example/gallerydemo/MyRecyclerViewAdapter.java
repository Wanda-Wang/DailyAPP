package com.example.gallerydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    private List<MyImage> myImageList = new ArrayList<>();
    private  OnRecyclerItemClickListener onRecyclerItemClickListener;

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

    public MyRecyclerViewAdapter (Context context, List<MyImage> myImages,
                                  OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.myImageList = myImages;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(onRecyclerItemClickListener!=null){
                    onRecyclerItemClickListener.onItemClick(view, (int)view.getTag());
                }
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyImage myImage = myImageList.get(position);
        holder.itemView.setTag(position);
        holder.imageView.setImageResource(myImage.getImageeId());
    }

    @Override
    public int getItemCount() {
        return myImageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView = null;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
