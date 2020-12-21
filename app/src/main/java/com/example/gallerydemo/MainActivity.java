package com.example.gallerydemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<MyImage> myImageList = new ArrayList<>();
    private RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;
    RecyclerView.Adapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();;
        if (actionBar != null) {
            actionBar.hide();
        }

        requestWritePermission();
        initData();
        initWidgets();
    }

    /**
     * 初始化数据
     */
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

    /**
     * 初始化控件
     */
    private void initWidgets(){
        //设置recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
        recyclerView.setHasFixedSize(true);
        //创建线性布局
        layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //初始化适配器
        adapter = new MyImageAdapter(this, myImageList,
                R.layout.item_recyclerview_main, R.id.image_item_recyclerview_main,
                new MyImageAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击了"+position+"项", Toast.LENGTH_SHORT).show();
                //跳转到相簿
                Intent intent = new Intent(MainActivity.this, AlbumActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }


    private void requestWritePermission(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

}