package com.example.gallerydemo;

public class MyImage {
    private int myImageId;
    private int myImageSize;
    private String myImagePath;
    private String myImageDisplayName;

    public MyImage(int myImageId) {
        this.myImageId = myImageId;
    }

    public MyImage(int myImageSize, String myImagePath, String myImageDisplayName) {
//        this.myImageId = myImageId;
        this.myImageSize = myImageSize;
        this.myImagePath = myImagePath;
        this.myImageDisplayName = myImageDisplayName;
    }

    public int getMyImageId() {
        return myImageId;
    }

    public String getMyImagePath() {
        return myImagePath;
    }


}
