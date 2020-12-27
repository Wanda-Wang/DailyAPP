package com.example.gallerydemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gallerydemo.Bean.MyImage;
import com.example.gallerydemo.R;
import com.example.gallerydemo.ViewPagerActivity;

import java.util.ArrayList;
import java.util.List;

public class MyEditImageAdapter extends RecyclerView.Adapter<MyEditImageAdapter.ViewHolder> {

    private List<MyImage> myImageList = new ArrayList<>();
    public static List<MyImage> mySelectedImageList = new ArrayList<>();
    private MyEditImageAdapter.OnRecyclerItemClickListener onRecyclerItemClickListener = null;
    private MyEditImageAdapter.OnRecyclerItemLongClickListener onRecyclerItemLongClickListener = null;
    private int inflateLayout = 0;
    private Context context = null;
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
     * 设置点击监听
     * @param onRecyclerItemClickListener 监听器
     */
    public void setOnRecyclerItemClickListener(
            MyEditImageAdapter.OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    /**
     * 设置长按监听
     * @param onRecyclerItemLongClickListener 监听器
     */
    public void setOnRecyclerItemLongClickListener(
            MyEditImageAdapter.OnRecyclerItemLongClickListener onRecyclerItemLongClickListener) {
        this.onRecyclerItemLongClickListener = onRecyclerItemLongClickListener;
    }

    /**
     * 自定义RecyclerView 中item view点击回调方法
     */
    public interface OnRecyclerItemClickListener{
        /**
         * item view 回调方法 点击
         * @param view  被点击的view
         * @param position 点击索引
         */
        void onItemClick(View view, int position);
    }

    /**
     * 自定义RecyclerView 中item view长按回调方法
     */
    public interface OnRecyclerItemLongClickListener{
        /**
         * item view 回调方法 长按
         * @param view  被点击的view
         * @param position 点击索引
         */
        void onLongItemClick(View view, int position);
    }


    /**
     * 构造适配器
     * @param context 上下文呢
     * @param myImages 图像数据
     * @param item recyclerview item
     * @param inflateLayout recyclerview布局
     */
    public MyEditImageAdapter(Context context, List<MyImage> myImages, int inflateLayout, int item ){
        this.context = context;
        this.inflateLayout = inflateLayout;
        this.myImageList = myImages;
        this.item = item;
    }

    /**
     * 构造适配器
     * @param context 上下文呢
     * @param myImages 图像数据
     * @param item recyclerview item
     * @param inflateLayout recyclerview布局
     * @param onRecyclerItemClickListener 点击监听器
     */
    public MyEditImageAdapter(Context context, List<MyImage> myImages, int inflateLayout, int item,
                              OnRecyclerItemClickListener onRecyclerItemClickListener
                              ){
        this.context = context;
        this.inflateLayout = inflateLayout;
        this.myImageList = myImages;
        this.item = item;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    /**
     * 构造适配器
     * @param context 上下文呢
     * @param myImages 图像数据
     * @param item recyclerview item
     * @param inflateLayout recyclerview布局
     * @param onRecyclerItemLongClickListener 点击监听器
     */
    public MyEditImageAdapter(Context context, List<MyImage> myImages, int inflateLayout, int item,
                              OnRecyclerItemLongClickListener onRecyclerItemLongClickListener){
        this.context = context;
        this.inflateLayout = inflateLayout;
        this.myImageList = myImages;
        this.item = item;
        this.onRecyclerItemLongClickListener = onRecyclerItemLongClickListener;
    }

    /**
     * 构造适配器
     * @param context 上下文呢
     * @param myImages 图像数据
     * @param item recyclerview item
     * @param inflateLayout recyclerview布局
     * @param onRecyclerItemLongClickListener 点击监听器
     */
    public MyEditImageAdapter(Context context, List<MyImage> myImages, int inflateLayout, int item,
                              OnRecyclerItemClickListener onRecyclerItemClickListener,
                              OnRecyclerItemLongClickListener onRecyclerItemLongClickListener){
        this.context = context;
        this.inflateLayout = inflateLayout;
        this.myImageList = myImages;
        this.item = item;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
        this.onRecyclerItemLongClickListener = onRecyclerItemLongClickListener;
    }

    /**
     * 创建ViewHolder
     * @param parent 父布局容器
     * @param viewType  布局类型
     * @return 子布局容器
     */
    @NonNull
    @Override
    public MyEditImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext())
                .inflate(inflateLayout, parent, false);
        MyEditImageAdapter.ViewHolder viewHolder = new MyEditImageAdapter.ViewHolder(view);

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
    public void onBindViewHolder(@NonNull final MyEditImageAdapter.ViewHolder holder, final int position) {
        final MyImage myImage = myImageList.get(position);
        holder.itemView.setTag(position);
        //点击
        if (onRecyclerItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "click ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), ViewPagerActivity.class);
                    intent.putExtra("position", position);
                    //添加过渡动画
                    ActivityOptionsCompat activityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    (Activity) v.getContext(),
                                    holder.itemView.findViewById(R.id.image_item_recyclerview_edit_image),
                                    "share_element");
                    ActivityCompat.startActivity(v.getContext(), intent, activityOptionsCompat.toBundle());
                }
            });
            //Glide缓冲
            Glide.with(this.context)
                    .load(myImage.getMyImagePath())
                    .centerCrop()
                    .into(holder.imageView);

        }
        //长按
        if (onRecyclerItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //该图片已经选择
                    if (mySelectedImageList.contains(myImageList.get(position))){
                        Toast.makeText(v.getContext(), "longclick ", Toast.LENGTH_SHORT).show();
                        holder.imageView.setPadding(0,0,0,0);
                        //Glide缓冲
                        Glide.with(v.getContext())
                                .load(myImage.getMyImagePath())
                                .centerCrop()
                                .into(holder.imageView);
                        mySelectedImageList.remove(myImageList.get(position));
                    }
                    //未选择
                    else {
                        Toast.makeText(v.getContext(), "longclick ", Toast.LENGTH_SHORT).show();
                        holder.imageView.setPadding(20, 20, 20, 20);
                        //Glide缓冲
                        Glide.with(v.getContext())
                                .load(myImage.getMyImagePath())
                                .centerCrop()
                                .into(holder.imageView);
                        mySelectedImageList.add(myImageList.get(position));
                    }

                    return true;
                }
            });
        }
            //Bitmap压缩
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 16;
//                Bitmap bitmap = BitmapFactory.decodeFile(myImage.getMyImagePath(), options);
//                holder.imageView.setImageBitmap(bitmap);


    }

    /**
     * 返回数据长度
     * @return 数据大小
     */
    @Override
    public int getItemCount() {
        return myImageList.size();
    }

}
