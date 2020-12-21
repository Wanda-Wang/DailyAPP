package com.example.gallerydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
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

public class EditImageActivity extends AppCompatActivity {


    private static final int PHOTO_FROM_GALLERY = 1;
    private static final int PHOTO_FROM_CAMERA = 2;
    private ImageView imageView = null;
    private Button buttonToGallery = null;
    final HashMap<String,List<MyImage>> allPhotosTemp = new HashMap<>();//所有照片
    private List<MyImage> myImageList = new ArrayList<>();
    private RecyclerView recyclerView = null;
    private StaggeredGridLayoutManager layoutManager = null;
    private RecyclerView.Adapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
//        initData();
        getAllPhotoInfo();
        initWidgets();
        //mSharedPreferences = SharedPreferences.getInstance(this);
        imageView = (ImageView) findViewById(R.id.image_item_recyclerview_edit_image);
//        buttonToGallery = (Button) findViewById(R.id.button_editimage_to_gallery);
//        buttonToGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gallery(v);
//            }
//        });

        //getAllPhotoInfo();
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
     * 读取手机中所有图片信息
     */
    private void getAllPhotoInfo() {
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
//        String imagePath = myImageList.get(0).getMyImagePath();
//        imageView.setImageURI(Uri.parse(imagePath));
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_edit_image);
        recyclerView.setHasFixedSize(true);
        //创建布局
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //初始化适配器
        adapter = new MyImageAdapter(this, myImageList,
                R.layout.item_recyclerview_edit_image, R.id.image_item_recyclerview_edit_image,
                new MyImageAdapter.OnRecyclerItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(EditImageActivity.this, "点击了"+position+"项",
                                Toast.LENGTH_SHORT).show();
                        //跳转到ViewPager
                        Intent intent = new Intent(EditImageActivity.this, ViewPagerActivity.class);
                        startActivity(intent);
                    }

                }){
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                MyImage myImage = myImageList.get(position);
                holder.itemView.setTag(position);

                //Bitmap压缩
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 16;
//                Bitmap bitmap = BitmapFactory.decodeFile(myImage.getMyImagePath(), options);
//                holder.imageView.setImageBitmap(bitmap);

                //Glide缓冲
                Glide.with((Context) EditImageActivity.this).load(myImage.getMyImagePath()).into(holder.imageView);

            }
        };
        recyclerView.setAdapter(adapter);

    }


}