package com.example.df_daily.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.df_daily.ui.home.HomeFragment;
import com.example.df_daily.bean.MyImage;
import com.example.df_daily.ui.home.HomeFragment;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private Context context = null;
    private List<MyImage> myImageList;
    public MyPagerAdapter(Context context, List<MyImage> myImageList){
        this.context = context;
        this.myImageList=myImageList;

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
