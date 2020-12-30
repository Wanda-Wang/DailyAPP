package com.example.df_daily;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.df_daily.Adapter.MyAlbumImageAdapter;
import com.example.df_daily.Adapter.MyEditImageAdapter;
import com.example.df_daily.bean.MyImage;
import com.example.df_daily.ui.home.HomeFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class AlbumActivity extends AppCompatActivity {
    private static final int PHOTO_FROM_GALLERY = 1;
    private static final int PHOTO_FROM_CAMERA = 2;
    private static final String TAG = "wanda AlbumActivity";
    public static List<MyImage> myImageList;
    private ImageView imageView = null;
    private Button button = null;
    private RecyclerView recyclerView = null;
    private StaggeredGridLayoutManager layoutManager = null;
    private MyAlbumImageAdapter adapter = null;
    private AlertDialog.Builder builder = null;
    private ArrayList<String> itemsSelectAlbum = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        requestWritePermission();
//        initButton();
//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_edit_image_activity);
//        relativeLayout.addView(View.inflate(this, R.layout.button_confirm, null));
    }

    @Override
    protected void onResume() {
        initData();
        initRecyclerView();
        super.onResume();
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


    public void initData(){
        myImageList=new ArrayList<MyImage>();
        String albumName;
        Intent intent=getIntent();
        albumName=intent.getStringExtra("albumName");
        Log.i(TAG,"albumName:"+albumName);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String pathStr=Environment.getExternalStorageDirectory() + File.separator+ ".photo"+ File.separator+albumName+ File.separator;
            File path=new File(pathStr);
            File[] files = path.listFiles();// 读取文件夹下文件
            if(files!=null){
                for(int i=0;i<files.length;i++){
                    Log.i(TAG,"FileName:"+files[i].getName());
                    myImageList.add(new MyImage(pathStr+files[i].getName(),files[i].getName()));
                }
            }
        }
    }

    /**
     * 初始化控件
     */
    public void initRecyclerView(){
        //设置recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_album);
        recyclerView.setHasFixedSize(true);
        //创建布局
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //初始化适配器
        adapter = new MyAlbumImageAdapter(this, myImageList,
                R.layout.item_recyclerview_album, R.id.image_item_recyclerview_album,
                new MyAlbumImageAdapter.OnRecyclerItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(AlbumActivity.this, "点击了"+position+"项",
                                Toast.LENGTH_SHORT).show();
                        //跳转到ViewPager
                        Intent intent = new Intent(AlbumActivity.this, ViewPagerActivity.class);
                        startActivity(intent);
                    }

                });

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }
}