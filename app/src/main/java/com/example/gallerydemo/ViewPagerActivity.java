package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyPagerAdapter(this));
 //       viewPager.setPageTransformer(false, new ParallaxTransformer());
        //设置当前图片位置
        viewPager.setCurrentItem(position);

    }


}