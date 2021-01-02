package com.example.df_daily.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.df_daily.bean.MyAlbum;
import com.example.df_daily.bean.MyImage;
import com.example.df_daily.ParallaxImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyMainImageAdapter extends RecyclerView.Adapter <MyMainImageAdapter.ViewHolder> {

    private List<MyAlbum> myImageList = new ArrayList<>();
    private OnRecyclerItemClickListener onRecyclerItemClickListener = null;
    private int inflateLayout = 0;
    private Context context = null;
    private static int imageItem;
    private static int dateItem;
    private static int albumNameItem;
    /** 年-月-日 显示格式 */
    public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";

    private static SimpleDateFormat simpleDateFormat;

    /**
     * 定义ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView = null;
        public TextView tvAlbumTime=null, tvAlbumName=null;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(imageItem);
            tvAlbumName=(TextView)itemView.findViewById(albumNameItem);
            tvAlbumTime=(TextView)itemView.findViewById(dateItem);
            if(imageView instanceof ParallaxImageView) {
                ((ParallaxImageView) imageView).setParallaxTranslation();
            }
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
     * @param imageItem recyclerview item
     * @param dateItem recyclerview item
     * @param albumNameItem item内相册名
     * @param inflateLayout recyclerview布局
     * @param onRecyclerItemClickListener 监听
     */
    public MyMainImageAdapter(Context context, List<MyAlbum> myImages, int inflateLayout, int imageItem,int dateItem,int albumNameItem,
                              OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.context = context;
        this.inflateLayout = inflateLayout;
        this.myImageList = myImages;
        this.imageItem = imageItem;
        this.dateItem=dateItem;
        this.albumNameItem=albumNameItem;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        MyAlbum myImage = myImageList.get(position);
        holder.itemView.setTag(position);
        holder.tvAlbumTime.setText(getStringDateShort(myImage.getBuildDate()));
        holder.tvAlbumName.setText(myImage.getAlbumName());
        //Glide缓冲
        Glide.with(this.context)
                .load(myImage.getFirstPhotoPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            ((ParallaxImageView) holder.imageView).setParallaxTranslation();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ((ParallaxImageView) holder.imageView).setParallaxTranslation();
                        return false;
                    }
                })
                .into(holder.imageView);

        //Bitmap压缩
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 16;
//                Bitmap bitmap = BitmapFactory.decodeFile(myImage.getMyImagePath(), options);
//                holder.imageView.setImageBitmap(bitmap);


    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
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
