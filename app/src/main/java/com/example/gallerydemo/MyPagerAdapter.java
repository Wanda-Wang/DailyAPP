package com.example.gallerydemo;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private static final int[] sDrawables = {R.mipmap.img1, R.mipmap.img2,
            R.mipmap.img3, R.mipmap.img4, R.mipmap.img5, R.mipmap.img6,
            R.mipmap.img7, R.mipmap.img8};

    @Override
    public int getCount() {
        return sDrawables.length;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        //添加照片
        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setImageResource(sDrawables[position]);
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
