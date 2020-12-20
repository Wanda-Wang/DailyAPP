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
    private int inflateLayout;
    private static int item;

    /**
     * 定义ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView = null;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(item);
        }
    }

    /**
     * 设置监听
     * @param onRecyclerItemClickListener 监听器
     */
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

    /**
     * 构造适配器
     * @param context 上下文呢
     * @param myImages 图像数据
     * @param item recyclerview item
     * @param inflateLayout recyclerview布局
     * @param onRecyclerItemClickListener 监听
     */
    public MyImageAdapter(Context context, List<MyImage> myImages, int inflateLayout, int item,
                          OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.inflateLayout = inflateLayout;
        this.mImageList = myImages;
        this.item = item;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    /**
     * 创建ViewHolder
     * @param parent 父布局容器
     * @param viewType  布局类型
     * @return 子布局容器
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext())
                .inflate(inflateLayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        //绑定监听
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

    /**
     * 绑定数据
     * @param holder 布局容器
     * @param position 数据位置
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyImage myImage = mImageList.get(position);
        holder.itemView.setTag(position);
        holder.imageView.setImageResource(myImage.getImageeId());

    }

    /**
     * 返回数据长度
     * @return 数据大小
     */
    @Override
    public int getItemCount() {
        return mImageList.size();
    }

}
