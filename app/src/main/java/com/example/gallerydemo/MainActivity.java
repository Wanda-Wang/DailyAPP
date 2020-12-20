package com.example.gallerydemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<MyImage> myImageList = new ArrayList<>();
    private RecyclerView mRecyclerView = null;
    RecyclerView.LayoutManager mLayoutManager = null;
    RecyclerView.Adapter mAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();;
        if (actionBar != null) {
            actionBar.hide();
        }

        initWidgets();
        initData();


        mAdapter = new MyRecyclerViewAdapter(this, myImageList, new MyRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击了"+position+"项", Toast.LENGTH_SHORT).show();
                //跳转到相簿
                Intent intent = new Intent(MainActivity.this, AlbumActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initWidgets(){
        //设置recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void initData(){
        myImageList.add(new MyImage(R.mipmap.img1));
        myImageList.add(new MyImage(R.mipmap.img2));
        myImageList.add(new MyImage(R.mipmap.img3));
        myImageList.add(new MyImage(R.mipmap.img4));
        myImageList.add(new MyImage(R.mipmap.img5));
        myImageList.add(new MyImage(R.mipmap.img6));
        myImageList.add(new MyImage(R.mipmap.img7));
        myImageList.add(new MyImage(R.mipmap.img8));
    }
}