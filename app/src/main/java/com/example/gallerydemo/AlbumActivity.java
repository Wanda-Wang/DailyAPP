package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AlbumActivity extends AppCompatActivity {
    private List<MyImage> myImageList = new ArrayList<>();
    private RecyclerView mRecyclerView = null;
    private StaggeredGridLayoutManager mLayoutManager = null;
    private RecyclerView.Adapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
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
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_album);
        mRecyclerView.setHasFixedSize(true);
        //创建布局
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        //初始化适配器
        mAdapter = new MyImageAdapter(this, myImageList,
                R.layout.item_recyclerview_album, R.id.image_item_recyclerview_album,
                new MyImageAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AlbumActivity.this, "点击了"+position+"项",
                        Toast.LENGTH_SHORT).show();
                //跳转到ViewPager
                Intent intent = new Intent(AlbumActivity.this, ViewPagerActivity.class);
                startActivity(intent);
            }

        });
        mRecyclerView.setAdapter(mAdapter);

    }
}