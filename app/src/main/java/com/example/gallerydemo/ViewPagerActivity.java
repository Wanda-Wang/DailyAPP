package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinglan.scrolllayout.ScrollLayout;

public class ViewPagerActivity extends AppCompatActivity {


    private ScrollLayout mScrollLayout;
    private TextView text_foot;
    private TextView arrow_icon;

    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
//                mScrollLayout.getBackground().setAlpha(255 - (int) precent);
                text_foot.animate().setDuration(500).rotation(180);
            }
            else text_foot.animate().setDuration(500).rotation(0);
            if (text_foot.getVisibility() == View.VISIBLE){
//                text_foot.setVisibility(View.GONE);
//                text_foot.setText("下滑关闭");
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                text_foot.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyPagerAdapter(this));
 //       viewPager.setPageTransformer(false, new ParallaxTransformer());
        //设置当前图片位置
        viewPager.setCurrentItem(position);
        initView();

    }

    private void initView() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.root);
        mScrollLayout = (ScrollLayout) findViewById(R.id.scroll_down_layout);
        text_foot = (TextView) findViewById(R.id.text_foot);
//        ListView listView = (ListView) findViewById(R.id.list_view);
//        listView.setAdapter(new ListviewAdapter(this));

        /**设置 setting*/
        mScrollLayout.setMinOffset(0);
        mScrollLayout.setMaxOffset((int) (ScreenUtil.getScreenHeight(this) * 0.5));
        mScrollLayout.setExitOffset(ScreenUtil.dip2px(this, 100));
        mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.setToClosed();
        mScrollLayout.setToExit();
//        mScrollLayout.getBackground().setAlpha(255);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollLayout.scrollToExit();
            }
        });
        text_foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollLayout.setToOpen();
            }
        });
    }

}