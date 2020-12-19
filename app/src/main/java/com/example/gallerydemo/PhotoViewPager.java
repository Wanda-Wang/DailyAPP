package com.example.gallerydemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import java.util.jar.Attributes;

public class PhotoViewPager extends ViewPager {
    public PhotoViewPager(@NonNull Context context) {
        super(context);
    }

    public PhotoViewPager (Context context, AttributeSet attrs) {
        super (context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent (MotionEvent motionEvent){
        try {
            return super.onInterceptTouchEvent(motionEvent);
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            return false;
        }
    }
}
