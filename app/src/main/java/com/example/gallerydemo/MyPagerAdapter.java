package com.example.gallerydemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private Context context = null;

    public MyPagerAdapter(Context context){
        this.context = context;

    }
    @Override
    public int getCount() {
        return MainActivity.myImageList.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        //添加照片
        final PhotoView photoView = new PhotoView(container.getContext());
        MyImage myImage = MainActivity.myImageList.get(position);
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
