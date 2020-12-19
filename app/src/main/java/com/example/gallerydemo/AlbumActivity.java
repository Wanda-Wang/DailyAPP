package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    public List<Imagee> imageeList = new ArrayList<>();
    private static final int REQUESTCODE_ALBUM = 1001;
    private String photoPath = null;
    private ImageView mImage = null;
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
        ImageeAdapter imageeAdapter = new ImageeAdapter(imageeList);
        recyclerView.setAdapter(imageeAdapter);
    }
}