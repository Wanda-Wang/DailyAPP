package com.example.df_daily.bean;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAlbum {
    /** 年-月-日 显示格式 */
    public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";

    private static SimpleDateFormat simpleDateFormat;

    private Date buildDate;
    private String albumName;
    private String firstPhotoPath;

    public MyAlbum(Date buildDate,String albumName,String firstPhotoPath){
        this.buildDate=buildDate;
        this.albumName=albumName;
        this.firstPhotoPath=firstPhotoPath;
    }
    public Date getBuildDate(){
        return buildDate;
    }
    public String getAlbumName(){
        return albumName;
    }
    public String getFirstPhotoPath(){
        return firstPhotoPath;
    }
    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }
}
