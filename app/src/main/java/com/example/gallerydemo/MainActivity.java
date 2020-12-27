package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.gallerydemo.Adapter.MyMainImageAdapter;
import com.example.gallerydemo.Bean.MyImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class MainActivity extends AppCompatActivity {
    public static List<MyImage> myImageList = new ArrayList<>();
    private RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;
    RecyclerView.Adapter adapter = null;
    final HashMap<String,List<MyImage>> allPhotosTemp = new HashMap<>();//所有照片
    public ArrayList<String> ExistedAlbum = new ArrayList<String>();
    private int recyclerviewHeight = -1;
    private int[] recyclerviewLocation = {-1, -1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestWritePermission();
        getAllPhotoInfo();
        initWidgets();

    }

    /**
     * 读取手机中所有图片信息
     */
    public void getAllPhotoInfo() {
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = { MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                ,MediaStore.Images.Media.SIZE
                ,MediaStore.Images.Media.DISPLAY_NAME};
        Cursor mCursor = getContentResolver().query(imageUri,
                projImage,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED+" desc");

        if(mCursor!=null){
            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                int size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024;
                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                //用于展示相册初始化界面
                //myImageList.add(new MyImage(MyImage.Type.Image,path,size,displayName));
                myImageList.add(new MyImage(size,path,displayName));
                // 获取该图片的父路径名
                String dirPath = new File(path).getParentFile().getAbsolutePath();
                //存储对应关系
                if (allPhotosTemp.containsKey(dirPath)) {
                    List<MyImage> data = allPhotosTemp.get(dirPath);
                    data.add(new MyImage(size, path, displayName));
                    continue;
                } else {
                    List<MyImage> data = new ArrayList<>();
                    data.add(new MyImage(size, path, displayName));
                    allPhotosTemp.put(dirPath,data);
                }
            }
            mCursor.close();
        }
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
        adapter = new MyMainImageAdapter(this, myImageList,
                R.layout.item_recyclerview_main, R.id.image_item_recyclerview_main,
                new MyMainImageAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击了"+position+"项", Toast.LENGTH_SHORT).show();
                //跳转到相簿
                Intent intent = new Intent(MainActivity.this, AlbumActivity.class);
                startActivity(intent);
            }
        });
        //添加动画并绑定适配器
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(scaleInAnimationAdapter);
//        recyclerView.setAdapter(adapter);
        recyclerView.setTag(ParallaxImageView.RECYCLER_VIEW_TAG);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if(recyclerviewHeight == -1){
//                    recyclerviewHeight = recyclerView.getHeight();
//                    recyclerView.getLocationOnScreen(recyclerviewLocation);
//
//                }
//
//            }
//        });
    }


    private void requestWritePermission(){
        //动态获取GPS定位权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

}