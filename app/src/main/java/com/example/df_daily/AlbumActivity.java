package com.example.df_daily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.example.df_daily.Adapter.MyAlbumImageAdapter;
import com.example.df_daily.Adapter.MyEditImageAdapter;
import com.example.df_daily.Helper.DbController;
import com.example.df_daily.Helper.SharedHelper;
import com.example.df_daily.Layout.ButtonChangeLayout;
import com.example.df_daily.bean.MyImage;
import com.example.df_daily.bean.PhotoInfo;
import com.example.df_daily.ui.home.HomeFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
    private DbController mDbController;
    private PhotoInfo photoInfo;
//    private dateThread dateThread;
    private SharedHelper sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        sp=new SharedHelper(this);
        sp.save_album(getIntent().getStringExtra("albumName"));
        Log.i(TAG,"sp读取"+sp.read_album().get("_albumName"));
        requestWritePermission();
        mDbController = DbController.getInstance(AlbumActivity.this);

//        Bundle bundle=new Bundle();
//        setTitle(intent.getStringExtra("albumName"));
//        bundle.putString("albumName",);
//        bundle.putString("buildDate",);
//        bundle.putString("first",intent.getStringExtra("first"));
////        getPhotoLocation(intent.getStringExtra("first"));
//        msg.setData(bundle);

//        dateThread.mHandler.sendMessage(msg);
//        Log.i(TAG,"dateThread.mHandler"+dateThread.mHandler);


    }

    @Override
    protected void onResume() {
//        dateThread=new dateThread();
//        dateThread.start();
//        Message msg=new Message();
//        msg.what=0x123;
        Intent intent=getIntent();
        String albumName=intent.getStringExtra("albumName");
        String buildDate=intent.getStringExtra("buildDate");
        setTitle(albumName);
        initData(albumName,buildDate);
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


//    //子线程进行文件、数据库相关耗时操作，通过handler，主线程向子线程发送消息
//    class dateThread extends Thread{
//        public Handler mHandler;
//
//        @Override
//        public void run() {
//            Looper.prepare();
//            mHandler=new Handler(){
//                @Override
//                public void handleMessage(@NonNull Message msg) {
//                    if(msg.what==0x123){
//                        String albumName=msg.getData().getString("albumName");
//                        String buildDate=msg.getData().getString("buildDate");
//                        initData(albumName,buildDate);
//
//
//                    }
//                }
//            };
//            Looper.loop();
//        }
//    }
    public void initData(String albumName,String buildDate){
        myImageList=new ArrayList<MyImage>();
        Log.i(TAG,"albumName:"+albumName);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String pathStr=Environment.getExternalStorageDirectory() + File.separator+ ".photo"+ File.separator+albumName+ File.separator;
            File path=new File(pathStr);
            File[] files = path.listFiles();// 读取文件夹下文件
            if(files!=null){
                for(int i=0;i<files.length;i++){
                    Log.i(TAG,"FileName:"+files[i].getName());
                    if("".equals( mDbController.searchByWhere(files[i].getName()))){
                        myImageList.add(new MyImage(pathStr+files[i].getName(),files[i].getName(),albumName,"",buildDate));
                    }
                    else {
                        photoInfo = mDbController.searchByWhere(files[i].getName());
                        if(photoInfo==null){
                            myImageList.add(new MyImage(pathStr + files[i].getName(), files[i].getName(), albumName, "", buildDate));
                        }
                        else {
                            if (photoInfo.getStory() == null) {
                                myImageList.add(new MyImage(pathStr + files[i].getName(), files[i].getName(), albumName, "", buildDate));
                            } else {
                                String description = photoInfo.getStory();
                                myImageList.add(new MyImage(pathStr + files[i].getName(), files[i].getName(), albumName, description, buildDate));
                                Log.i(TAG, description);
                            }

                        }
                    }


                }
            }
        }
    }

    /**
     * 初始化控件
     */
    public void initRecyclerView(){
        String albumName;
        Intent intent=getIntent();
        albumName=intent.getStringExtra("albumName");
        //设置recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_album);
        recyclerView.setHasFixedSize(true);
        //创建布局
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //初始化适配器
        adapter = new MyAlbumImageAdapter(this, myImageList,
                R.layout.item_recyclerview_album, R.id.image_item_recyclerview_album,albumName,intent.getStringExtra("buildDate"),
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

    public LatLng getPhotoLocation(String imagePath){
        float output1=0.0f;
        float output2=0.0f;
        LatLng latLng=null;
        try {
            ExifInterface exifInterface= new ExifInterface(imagePath);
//            String datetime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);// 拍摄时间
//            String deviceName = exifInterface.getAttribute(ExifInterface.TAG_MAKE);// 设备品牌
//            String deviceModel = exifInterface.getAttribute(ExifInterface.TAG_MODEL); // 设备型号
            String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String lngRef = exifInterface.getAttribute
                    (ExifInterface.TAG_GPS_LONGITUDE_REF);
            if (latValue != null && latRef != null && lngValue != null && lngRef != null) {
                try {
                    output1 = convertRationalLatLonToFloat(latValue, latRef);
                    Log.i(TAG, String.valueOf(output1));
                    output2 = convertRationalLatLonToFloat(lngValue, lngRef);
                    Log.i(TAG, String.valueOf(output2));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

//            Toast.makeText(AlbumActivity.this, deviceName + ":" + deviceModel, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(AlbumActivity.this, output1 + ";" + output2 , Toast.LENGTH_LONG).show();

        latLng = new LatLng(output1 , output1 );
        return latLng;
    }
    private static float convertRationalLatLonToFloat(
            String rationalString, String ref) {

        String[] parts = rationalString.split(",");

        String[] pair;
        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[2].split("/");
        double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
        if ((ref.equals("S") || ref.equals("W"))) {
            return (float) -result;
        }
        return (float) result;
    }
}