package com.example.gallerydemo.Bean;

public class MyImage {
    private int myImageId;
    private int myImageSize;
    private String myImagePath;
    private String myImageDisplayName;
    private String myImageAlbum;
    private String myImageLocation;
    private String myImagePerson;

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

    public String getMyImageDisplayName() {
        return myImageDisplayName;
    }

}
