package com.example.df_daily.bean;

import java.util.Date;

public class MyImage {
    private int myImageId;
    private int myImageSize;
    private String myImagePath;
    private String myImageDisplayName;
    private int myImageAddDate;
    private String myImageAlbum;
    private String myImageLocation;
    private String myImagePerson;
    private String myDescription;
    private String myImageAlbumBuildTime;
    private String myImageType;

    public MyImage(int myImageId) {
        this.myImageId = myImageId;
    }

    public MyImage(String myImagePath, String myImageDisplayName,String myImageAlbum,String myDescription,String myImageAlbumBuildTime){
        this.myImagePath = myImagePath;
        this.myImageDisplayName = myImageDisplayName;
        this.myImageAlbum=myImageAlbum;
        this.myDescription=myDescription;
//        this.myImageType=myImageType;
        this.myImageAlbumBuildTime=myImageAlbumBuildTime;
    }
    public MyImage(int myImageSize, String myImagePath, String myImageDisplayName,int myImageAddDate) {
//        this.myImageId = myImageId;
        this.myImageSize = myImageSize;
        this.myImagePath = myImagePath;
        this.myImageDisplayName = myImageDisplayName;
        this.myImageAddDate=myImageAddDate;
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

}
