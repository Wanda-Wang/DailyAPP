package com.example.df_daily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.df_daily.Adapter.MyPagerAdapter;
import com.example.df_daily.Helper.ScreenUtil;
import com.example.df_daily.ui.home.HomeFragment;
import com.yinglan.scrolllayout.ScrollLayout;

import java.util.HashMap;

public class ViewPagerActivity extends AppCompatActivity {

    private static final String TAG = "wanda ViewPagerActivity";

    private ScrollLayout mScrollLayout;
    private RelativeLayout root;
    private EditText vp_addStory;
    private TextView displayName;
    private TextView text_foot;
    private TextView arrow_icon;
    private Button btn_ok,btn_edit;
    private RelativeLayout relativeLayout;
    HashMap<String,View> aMap;
    Context context;
    int viewPagerIndex;

    //判断AlbumActivity的recyclerView点开的ViewPager
    private final static int IS_MY_ALBUM=0x0003;
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

        context=this;
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position", 0);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        vp_addStory=findViewById(R.id.vp_addStory);
        displayName=findViewById(R.id.displayName);
//        btn_edit=findViewById(R.id.btn_edit);

        initView();
        aMap = new HashMap<String,View>();
        LayoutInflater li = getLayoutInflater();
        aMap.put("displayName",displayName);
        aMap.put("vp_addStory",vp_addStory);
        //判断读哪个List
        if(intent.getIntExtra("MyAlbum",0)==IS_MY_ALBUM){
            viewPager.setAdapter(new MyPagerAdapter(this,AlbumActivity.myImageList,aMap));
            displayName.setText(AlbumActivity.myImageList.get(position).getMyImageDisplayName());
            //单击编辑框
            vp_addStory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG,"setOnClickListener");
                    vp_addStory.setFocusableInTouchMode(true);
                    vp_addStory.setFocusable(true);
                    vp_addStory.setEnabled(true);
                    vp_addStory.requestFocus();
                }
            });
            //单击抽屉
            mScrollLayout.setOnTouchListener(new View.OnTouchListener() {
                private Context mContext=context;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i(TAG,"onTouch抽屉");
                        vp_addStory.setFocusable(false);
                        vp_addStory.clearFocus();
//                        vp_addStory.setGravity(Gravity.CENTER);
                        InputMethodManager imm=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(vp_addStory.getWindowToken(), 0);
                    return false;
                }
            });

        }
        else{
            viewPager.setAdapter(new MyPagerAdapter(this, HomeFragment.myImageList,aMap));
            vp_addStory.setHint("");
        }
 //       viewPager.setPageTransformer(false, new ParallaxTransformer());
        //设置当前图片位置
        viewPager.setCurrentItem(position);
        final int pos=position;
        final int myAlbumId=intent.getIntExtra("MyAlbum",0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int pos_=pos;
            private int currentPosition = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position>currentPosition) {
                    //右滑
                    Log.e("direction","right");
                    currentPosition=position;
                    if(myAlbumId==IS_MY_ALBUM){
                        pos_++;
                        displayName.setText(AlbumActivity.myImageList.get(pos_).getMyImageDisplayName());
                    }
                }else if (position<currentPosition){
                    //左滑
                    Log.e("direction","left");
                    currentPosition=position;
                    if(myAlbumId==IS_MY_ALBUM){
                        pos_--;
                        displayName.setText(AlbumActivity.myImageList.get(pos_).getMyImageDisplayName());

                    }
                }

//                if(viewPagerIndex ==position){
//                    Log.d(TAG,"正在向左滑动");
//                    if(pos_!=0){
//                        pos_--;
//                    }
//                    if(myAlbumId==IS_MY_ALBUM){
//                        displayName.setText(AlbumActivity.myImageList.get(pos_).getMyImageDisplayName());
//                    }
//                }else{
//                    Log.d(TAG,"正在向右滑动");
//                    if(myAlbumId==IS_MY_ALBUM){
////                        if(pos_!=AlbumActivity.myImageList.size()-1){
//                            pos_++;
////                        }
//
//                            displayName.setText(AlbumActivity.myImageList.get(pos_).getMyImageDisplayName());
//
//                    }
//                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        relativeLayout = (RelativeLayout) findViewById(R.id.root);
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