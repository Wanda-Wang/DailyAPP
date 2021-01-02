package com.example.df_daily.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.df_daily.AlbumActivity;
import com.example.df_daily.R;
import com.example.df_daily.ui.home.HomeFragment;
import com.example.df_daily.bean.MyImage;
import com.example.df_daily.ui.home.HomeFragment;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.HashMap;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private Context context = null;
    private List<MyImage> myImageList;
    private HashMap<String,View> aMap;
    private EditText vp_addStory;
    private TextView  displayName;
    public MyPagerAdapter(Context context, List<MyImage> myImageList, HashMap<String,View> aMap){
        this.context = context;
        this.myImageList=myImageList;
        this.aMap=aMap;


    }
    @Override
    public int getCount() {
        return myImageList.size();
    }


    @Override
    public View instantiateItem(ViewGroup container, int position) {
        //添加照片
        final PhotoView photoView = new PhotoView(container.getContext());
        MyImage myImage =myImageList.get(position);
//        Picasso.with(this.context)
//                .load(Uri.parse(myImage.getMyImagePath()))
//                .resize(200,200)
//                .into(photoView);

        Glide.with(this.context)
                .load(myImage.getMyImagePath())
                .into(photoView);

        //适配view
        container.addView(photoView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
