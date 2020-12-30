package com.example.df_daily.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.df_daily.Adapter.MyEditImageAdapter;
import com.example.df_daily.Helper.SharedHelper;
import com.example.df_daily.bean.MyAlbum;
import com.example.df_daily.bean.MyImage;
import com.example.df_daily.Helper.SDFileHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CopyService extends Service {
    private final String TAG ="wanda SaveService";
    SaveBinder binder_save=new SaveBinder();
    List<MyImage> mySelectedImageList;
    SDFileHelper sdFileHelper=new SDFileHelper(getBaseContext());
    SharedHelper sp;
    List<MyAlbum> albums;
//    Intent intent=getIntent();
    public CopyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i(TAG,"onBind");
//       return binder_save;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sp=new SharedHelper(getBaseContext());
        mySelectedImageList= MyEditImageAdapter.mySelectedImageList;
        if(sp.readAlbum()==null){
            albums=new ArrayList<MyAlbum>();
        }
        else{
            albums=sp.readAlbum();
        }

        if(intent!=null){
            Log.i(TAG,"onStartCommand");
            final String albumName=intent.getStringExtra("albumName");
            new Thread(new Runnable() {
                @Override
                public void run() {

                    //选中图片数大于零才可以添加相册到sp且新建相册文件夹
//                    if(mySelectedImageList.size()>0){
//                    MyImage myImage=new MyImage();
                        int i=0;
                        for(MyImage myImage:mySelectedImageList){
                            String filename=myImage.getMyImageDisplayName();
                            //附件文件路径
                            String toFile= Environment.getExternalStorageDirectory() + File.separator+".photo"+ File.separator+albumName+ File.separator+ filename;
                            //附件目录路径
                            String dir= Environment.getExternalStorageDirectory() + File.separator+ ".photo"+ File.separator+albumName+ File.separator;
                            //新建附件目录
                            File newFile = new File(dir);
                            if (!newFile.exists()) {
                                newFile.mkdirs();
                                Log.i(TAG,"新建文件夹");
                            }
                            //在附件目录下新建文件
                            if(newFile.exists()){
                                File file = new File(newFile.getAbsolutePath() + File.separator + filename);
                                if (!file.exists()) {
                                    try {
                                        file.createNewFile();
                                        Log.i(TAG,"新建文件");
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }
                                }
                            }
                            else {
                                Log.e(TAG,"新建文件夹不存在");
                            }

                            try {
                                //复制sd卡照片到附件
                                sdFileHelper.CopySdcardFile(myImage.getMyImagePath(),toFile);
                                Log.i(TAG,"附件已经复制");
                                Log.i(TAG,myImage.getMyImagePath());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if(i==0){
                                    albums.add(new MyAlbum(new Date(System.currentTimeMillis()),albumName,toFile));
                                    sp.saveAlbum(albums);
                            }
                            i++;

                        }



                    }
//                    else Toast.makeText(getBaseContext(),"请先添加图片",Toast.LENGTH_SHORT).show();

//                }
            }).start();

        }


        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    public class SaveBinder extends Binder {


        public void savePhoto(String ablumName, String photoName){
            File file=new File(Environment.getDataDirectory()+"/"+getPackageName()+"/.photo"+"/"+ablumName,photoName);
        }    }
}