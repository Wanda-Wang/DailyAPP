package com.example.df_daily.bean;

import android.provider.MediaStore;

import java.util.Date;

public class MyImage {
    private int myImageId;
    private float myImageSize;
    private String myImagePath;
    private String myImageDisplayName;
    private int myImageAddDate;
    private String myImageAlbum;
    private String myImageLocation;
    private String myImagePerson;
    private String myDescription;
    private String myImageAlbumBuildTime;
    private String myImageType;
    private float longitude;
    private float latitude;

    public MyImage(int myImageId) {
        this.myImageId = myImageId;
    }

    public MyImage(String myImagePath, String myImageDisplayName,String myImageAlbum,String myDescription,String myImageAlbumBuildTime){
        this.myImagePath = myImagePath;
        this.myImageDisplayName = myImageDisplayName;
        this.myImageAlbum=myImageAlbum;
        this.myDescription=myDescription;
        this.myImageAlbumBuildTime=myImageAlbumBuildTime;
    }
    public MyImage(float myImageSize, String myImagePath, String myImageDisplayName,float latitude,float longitude) {
        this.myImageSize = myImageSize;
        this.myImagePath = myImagePath;
        this.myImageDisplayName = myImageDisplayName;
        //经度
        this.latitude=latitude;
        //纬度
        this.longitude=longitude;
    }

    public String getMyDescription() {
        return myDescription;
    }

    public int getMyImageAddDate(){
        return myImageAddDate;
    }

    public int getMyImageId() {
        return myImageId;
    }

    public String getMyImagePath() {
        return myImagePath;
    }

    public String getMyImageDisplayName() {
        return myImageDisplayName;
    }

    //获取经度
    public float getLatitude() {
        return latitude;
    }
    //获取纬度
    public float getLongitude() {
        return longitude;
    }
}
