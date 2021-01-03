package com.example.df_daily.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.df_daily.R;
import com.example.df_daily.ViewPagerActivity;
import com.example.df_daily.bean.MyImage;
import com.example.df_daily.bean.FinalData;

import java.util.ArrayList;
import java.util.List;

public class MyAlbumImageAdapter extends RecyclerView.Adapter<MyAlbumImageAdapter.ViewHolder> {

    private List<MyImage> myImageList = new ArrayList<>();
    private MyAlbumImageAdapter.OnRecyclerItemClickListener onRecyclerItemClickListener = null;
    private int inflateLayout = 0;
    private  String albumName,date;
    private Context context = null;
    private static int item;
    //判断AlbumActivity的recyclerView点开的ViewPager
    private final static int IS_MY_ALBUM=0x0003;

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
    public void setOnRecyclerItemClickListener(MyAlbumImageAdapter.OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    /**
     * 自定义RecyclerView 中item view点击回调方法
     */
    public interface OnRecyclerItemClickListener{
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
    public MyAlbumImageAdapter(Context context, List<MyImage> myImages, int inflateLayout, int item,String albumName,String date,
                               MyAlbumImageAdapter.OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.context = context;
        this.inflateLayout = inflateLayout;
        this.myImageList = myImages;
        this.item = item;
        this.date=date;
        this.albumName=albumName;
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
    public MyAlbumImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext())
                .inflate(inflateLayout, parent, false);
        MyAlbumImageAdapter.ViewHolder viewHolder = new MyAlbumImageAdapter.ViewHolder(view);

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
    public void onBindViewHolder(@NonNull final MyAlbumImageAdapter.ViewHolder holder, final int position) {
        MyImage myImage = myImageList.get(position);
        holder.itemView.setTag(position);
        //点击
        if (onRecyclerItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "click ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), ViewPagerActivity.class);
                    //如果是MyAlbumImage打开ViewPager,传值0x0001表示是相册List
                    intent.putExtra("MyAlbum",IS_MY_ALBUM);
                    intent.putExtra("position", position);
                    intent.putExtra("albumName",albumName);
                    intent.putExtra("date",date);
                    //添加过渡动画
                    ActivityOptionsCompat activityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    (Activity) v.getContext(),
                                    holder.itemView.findViewById(R.id.image_item_recyclerview_album),
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
