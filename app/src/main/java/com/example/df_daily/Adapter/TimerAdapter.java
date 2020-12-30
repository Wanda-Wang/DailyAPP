package com.example.df_daily.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.df_daily.ParallaxImageView;
import com.example.df_daily.R;
import com.example.df_daily.bean.MyAlbum;
import com.example.df_daily.bean.Trace;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimerAdapter extends BaseAdapter {
    private Context context;
    private List<MyAlbum> timerList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;
    /** 年-月-日 显示格式 */
    public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";

    private static SimpleDateFormat simpleDateFormat;

    public TimerAdapter(Context context, List<MyAlbum> traceList) {
        this.context = context;
        this.timerList = traceList;
    }

    @Override
    public int getCount() {
        return timerList.size();
    }

    @Override
    public MyAlbum getItem(int position) {
        return timerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final MyAlbum MyAlbum = getItem(position);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_timer, parent, false);
            holder.tvAlbumName = (TextView) convertView.findViewById(R.id.tvAlbumName);
            holder.tvAlbumTime = (TextView) convertView.findViewById(R.id.tvAlbumTime);
            holder.tvTopLine = (TextView) convertView.findViewById(R.id.tvTopLine);
            holder.tvDot = (TextView) convertView.findViewById(R.id.tvDot);
            holder.ivAlbumFirstPhoto=(ImageView)convertView.findViewById(R.id.ivAlbumFirstPhoto) ;
            holder.time=(TextView) convertView.findViewById(R.id.time);
            holder.AlbumName=(TextView) convertView.findViewById(R.id.AlbumName);


            //Glide缓冲
            Glide.with(this.context)
                    .load(MyAlbum.getFirstPhotoPath())
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            ((ParallaxImageView) holder.ivAlbumFirstPhoto).setParallaxTranslation();
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            ((ParallaxImageView) holder.ivAlbumFirstPhoto).setParallaxTranslation();
//                            return false;
//                        }
//                    })
                    .into(holder.ivAlbumFirstPhoto);
            convertView.setTag(holder);
        }

        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            holder.tvAlbumTime.setTextColor(0xff555555);
            holder.tvAlbumName.setTextColor(0xff555555);
            holder.time.setTextColor(0xff555555);
            holder.AlbumName.setTextColor(0xff555555);
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            holder.tvAlbumTime.setTextColor(0xff999999);
            holder.tvAlbumName.setTextColor(0xff999999);
            holder.time.setTextColor(0xff999999);
            holder.AlbumName.setTextColor(0xff999999);
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        }

        holder.tvAlbumTime.setText(getStringDateShort(MyAlbum.getBuildDate()).toString());
        holder.tvAlbumName.setText(MyAlbum.getAlbumName());
        return convertView;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public Date getNowDateShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
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

    @Override
    public int getItemViewType(int id) {
        if (id == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    static class ViewHolder {
        public TextView tvAlbumTime, tvAlbumName,time,AlbumName;
        public ImageView ivAlbumFirstPhoto;
        public TextView tvTopLine, tvDot;

    }
}