package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AlbumActivity extends AppCompatActivity {
    public List<MyImage> myImageList = new ArrayList<>();
    private static final int REQUESTCODE_ALBUM = 1001;
    private String photoPath = null;
    private ImageView mImage = null;
    public RecyclerView.Adapter mAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        init();
        show();

    }

    private void init(){
        myImageList.add(new MyImage(R.mipmap.img1));
        myImageList.add(new MyImage(R.mipmap.img2));
        myImageList.add(new MyImage(R.mipmap.img3));
        myImageList.add(new MyImage(R.mipmap.img4));
        myImageList.add(new MyImage(R.mipmap.img5));
        myImageList.add(new MyImage(R.mipmap.img6));
        myImageList.add(new MyImage(R.mipmap.img7));
        myImageList.add(new MyImage(R.mipmap.img8));

    }

    private void show(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_album);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyImageAdapter(this, myImageList, new MyImageAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AlbumActivity.this, "点击了"+position+"项",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AlbumActivity.this, ViewPagerActivity.class);
                startActivity(intent);
            }

        });
        recyclerView.setAdapter(mAdapter);

    }
}