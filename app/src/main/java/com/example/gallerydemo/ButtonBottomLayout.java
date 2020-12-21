package com.example.gallerydemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.RelativeLayout;
import com.getbase.floatingactionbutton.FloatingActionButton;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

public class ButtonBottomLayout extends RelativeLayout {

    Bundle bundle = new Bundle();
    private static final int PHOTO_FROM_GALLERY = 1;
    private static final int PHOTO_FROM_CAMERA = 2;
    private  FloatingActionButton buttonToGallery = null;

    public ButtonBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.button_bottom, this);
        bundle.putString("Data", "data from TestBundle");
        //监听浮动按键
        buttonToGallery = (FloatingActionButton) findViewById(R.id.button_to_gallery);
        buttonToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到编辑图库activity
                Intent intent = new Intent((Activity)v.getContext(), EditImageActivity.class);
                startActivity((Activity) v.getContext(), intent, bundle);
//                //跳转到图库
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.putExtras(bundle);
//                startActivityForResult((Activity) v.getContext(), intent, PHOTO_FROM_GALLERY, bundle);
            }
        });
    }
}
