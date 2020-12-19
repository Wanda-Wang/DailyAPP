package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    public List<Imagee> imageeList = new ArrayList<>();
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
        Imagee imagee1 = new Imagee(R.drawable.sanxia);
        imageeList.add(imagee1);
        Imagee imagee2 = new Imagee(R.drawable.ic_launcher);
        imageeList.add(imagee2);
        Imagee imagee3 = new Imagee(R.drawable.ic_launcher);
        imageeList.add(imagee3);
        Imagee imagee4 = new Imagee(R.drawable.ic_launcher);
        imageeList.add(imagee4);
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));
        imageeList.add(new Imagee(R.drawable.sanxia));
        imageeList.add(new Imagee(R.drawable.ground_overlay));
        imageeList.add(new Imagee(R.drawable.layers));
        imageeList.add(new Imagee(R.drawable.map));


    }

    private void show(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_album);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ImageeAdapter(this, imageeList, new ImageeAdapter.OnRecyclerItemClickListener() {
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