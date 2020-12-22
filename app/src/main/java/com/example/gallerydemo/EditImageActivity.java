package com.example.gallerydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class EditImageActivity extends AppCompatActivity {

    private static final int PHOTO_FROM_GALLERY = 1;
    private static final int PHOTO_FROM_CAMERA = 2;
    private ImageView imageView = null;
    private List<MyImage> myImageList = MainActivity.myImageList;
    private RecyclerView recyclerView = null;
    private StaggeredGridLayoutManager layoutManager = null;
    private RecyclerView.Adapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        initWidgets();
        imageView = (ImageView) findViewById(R.id.image_item_recyclerview_edit_image);
    }

    //从相册取图片
    public void gallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PHOTO_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //第一层switch
        switch (requestCode) {
            case PHOTO_FROM_GALLERY:
                //第二层switch
                switch (resultCode) {
                    case RESULT_OK:
                        if (data != null) {
                            Uri uri = data.getData();
                            imageView.setImageURI(uri);
                        }
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
            case PHOTO_FROM_CAMERA:
                break;
            default:
                break;
        }
    }


    /**
     * 初始化控件
     */
    private void initWidgets(){
        //设置recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_edit_image);
        recyclerView.setHasFixedSize(true);
        //创建布局
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //初始化适配器
        adapter = new MyEditImageAdapter(this, myImageList,
                R.layout.item_recyclerview_edit_image, R.id.image_item_recyclerview_edit_image);
        //                {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Toast.makeText(EditImageActivity.this, "点击了"+position+"项",
//                                Toast.LENGTH_SHORT).show();
//                        //跳转到ViewPager
//                        Intent intent = new Intent(EditImageActivity.this, ViewPagerActivity.class);
//                        intent.putExtra("position", position);
//                        startActivity(intent);
//                    }
//                }
        //设计动画并绑定适配器
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(scaleInAnimationAdapter);

    }


}