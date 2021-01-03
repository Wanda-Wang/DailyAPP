package com.example.df_daily.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DailyInfo {
    @Id(autoincrement = true)//设置自增长
    private Long id;

    @Index(unique = true)//设置唯一性
    private String dailyTitle;//日记标题

    private String dailyDescription;//日记内容

    private String dailyEmotion;//心情

    private String dailyWeather;//天气

    private String dailyDate;//日记添加时间

    @Generated(hash = 365985028)
    public DailyInfo(Long id, String dailyTitle, String dailyDescription,
            String dailyEmotion, String dailyWeather, String dailyDate) {
        this.id = id;
        this.dailyTitle = dailyTitle;
        this.dailyDescription = dailyDescription;
        this.dailyEmotion = dailyEmotion;
        this.dailyWeather = dailyWeather;
        this.dailyDate = dailyDate;
    }

    @Generated(hash = 439696465)
    public DailyInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDailyTitle() {
        return this.dailyTitle;
    }

    public void setDailyTitle(String dailyTitle) {
        this.dailyTitle = dailyTitle;
    }

    public String getDailyDescription() {
        return this.dailyDescription;
    }

    public void setDailyDescription(String dailyDescription) {
        this.dailyDescription = dailyDescription;
    }

    public String getDailyEmotion() {
        return this.dailyEmotion;
    }

    public void setDailyEmotion(String dailyEmotion) {
        this.dailyEmotion = dailyEmotion;
    }

    public String getDailyWeather() {
        return this.dailyWeather;
    }

    public void setDailyWeather(String dailyWeather) {
        this.dailyWeather = dailyWeather;
    }

    public String getDailyDate() {
        return this.dailyDate;
    }

    public void setDailyDate(String dailyDate) {
        this.dailyDate = dailyDate;
    }
}
