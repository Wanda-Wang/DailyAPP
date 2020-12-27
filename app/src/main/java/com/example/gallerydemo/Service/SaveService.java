package com.example.gallerydemo.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.example.gallerydemo.Adapter.MyEditImageAdapter;
import com.example.gallerydemo.Bean.MyImage;
import com.example.gallerydemo.Helper.SDFileHelper;

import java.io.File;
import java.util.List;

public class SaveService extends Service {
    private final String TAG ="wanda SaveService";
    SaveBinder binder_save=new SaveBinder();
    List<MyImage> mySelectedImageList= MyEditImageAdapter.mySelectedImageList;
    SDFileHelper sdFileHelper=new SDFileHelper(getBaseContext());
//    Intent intent=getIntent();
    public SaveService() {
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
        if(intent!=null){
            Log.i(TAG,"onStartCommand");
            final String albumName=intent.getStringExtra("albumName");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(MyImage myImage:mySelectedImageList){
                        String filename=myImage.getMyImageDisplayName();
                        //附件文件路径
                        String toFile=Environment.getExternalStorageDirectory() + File.separator+".photo"+File.separator+albumName+File.separator+ filename;
                        //附件目录路径
                        String dir=Environment.getExternalStorageDirectory() +File.separator+ ".photo"+File.separator+albumName+File.separator;
                        //新建附件目录
                        File newFile = new File(dir);
                        if (!newFile.exists()) {
                            newFile.mkdirs();
                            Log.i(TAG,"新建文件夹");
                        }
                        //在附件目录下新建文件
                        if(newFile.exists()){
                            Log.i(TAG,"新建文件");
                            File file = new File(newFile.getAbsolutePath() + File.separator + filename);
                            if (!file.exists()) {
                                try {
                                    file.createNewFile();
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
                    }
                }
            }).start();

        }


        return super.onStartCommand(intent, flags, startId);
    }

    public class SaveBinder extends Binder{


        public void savePhoto(String ablumName,String photoName){
            File file=new File(Environment.getDataDirectory()+"/"+getPackageName()+"/.photo"+"/"+ablumName,photoName);
        }    }
}