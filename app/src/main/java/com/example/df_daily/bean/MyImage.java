package com.example.df_daily.bean;

public class MyImage {
    private int myImageId;
    private int myImageSize;
    private String myImagePath;
    private String myImageDisplayName;
    private int myImageAddDate;
    private String myImageAlbum;
    private String myImageLocation;
    private String myImagePerson;

    public MyImage(int myImageId) {
        this.myImageId = myImageId;
    }

    public MyImage(String myImagePath, String myImageDisplayName){
        this.myImagePath = myImagePath;
        this.myImageDisplayName = myImageDisplayName;
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
