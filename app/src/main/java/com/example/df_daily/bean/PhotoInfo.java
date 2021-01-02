package com.example.df_daily.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PhotoInfo {
    @Id(autoincrement = true)//设置自增长
    private Long id;

    @Index(unique = true)//设置唯一性
    private String photoName;//文件名

    private String albumName;//相册名

    private String story;//故事

    private String date;//相册创建时间

    @Generated(hash = 1018881831)
    public PhotoInfo(Long id, String photoName, String albumName, String story,
            String date) {
        this.id = id;
        this.photoName = photoName;
        this.albumName = albumName;
        this.story = story;
        this.date = date;
    }

    @Generated(hash = 2143356537)
    public PhotoInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoName() {
        return this.photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getStory() {
        return this.story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
