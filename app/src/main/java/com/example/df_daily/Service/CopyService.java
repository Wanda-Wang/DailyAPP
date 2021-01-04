package com.example.df_daily.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.exifinterface.media.ExifInterface;

import com.baidu.mapapi.model.LatLng;
import com.example.df_daily.Adapter.MyEditImageAdapter;
import com.example.df_daily.AlbumActivity;
import com.example.df_daily.Helper.DbController;
import com.example.df_daily.Helper.SharedHelper;
import com.example.df_daily.ViewPagerActivity;
import com.example.df_daily.bean.MyAlbum;
import com.example.df_daily.bean.MyImage;
import com.example.df_daily.Helper.SDFileHelper;
import com.example.df_daily.bean.PhotoInfo;

import java.io.File;
import java.text.SimpleDateFormat;
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
    private DbController mDbController;
    private PhotoInfo photoInfo;
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
        mDbController = DbController.getInstance(getBaseContext());
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
                        int i=0;
                        for(MyImage myImage:mySelectedImageList){
                            Date date=new Date(System.currentTimeMillis());
                            String filename=myImage.getMyImageDisplayName();
                            //将选中照片信息添加到数据库
                            photoInfo=new PhotoInfo(null,filename,albumName,null,getStringDateShort(date),myImage.getLatitude(),myImage.getLongitude());
                            mDbController.insertOrReplace(photoInfo);
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
                                    albums.add(new MyAlbum(date,albumName,toFile));
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
            Log.i(TAG, String.valueOf(output1));
            Log.i(TAG, String.valueOf(output2));
//            Toast.makeText(AlbumActivity.this, deviceName + ":" + deviceModel, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Toast.makeText(getBaseContext(), output1 + ";" + output2 , Toast.LENGTH_LONG).show();

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

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}