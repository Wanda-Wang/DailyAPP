package com.example.df_daily.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.df_daily.bean.DaoMaster;
import com.example.df_daily.bean.DaoSession;
import com.example.df_daily.bean.PhotoInfo;
import com.example.df_daily.bean.PhotoInfoDao;

import java.util.List;

public class DbController {
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private PhotoInfoDao photoInfoDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context){
        if(mDbController == null){
            synchronized (DbController.class){
                if(mDbController == null){
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }
    /**
     * 初始化
     * @param context
     */
    public DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context,"photo.db", null);
        mDaoMaster =new DaoMaster(getWritableDatabase());
        mDaoMaster =new DaoMaster(getReadableDatabase());
        mDaoSession = mDaoMaster.newSession();
        photoInfoDao = mDaoSession.getPhotoInfoDao();
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase(){
        if(mHelper == null){
            mHelper = new DaoMaster.DevOpenHelper(context,"photo.db",null);
        }
        SQLiteDatabase db =mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     * @return
     */
    private SQLiteDatabase getWritableDatabase(){
        if(mHelper == null){
            mHelper =new DaoMaster.DevOpenHelper(context,"photo.db",null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     * @param photoInfo
     */
    public void insertOrReplace(PhotoInfo photoInfo){
        photoInfoDao.insertOrReplace(photoInfo);
    }
    /**插入一条记录，表里面要没有与之相同的记录
     *
     * @param photoInfo
     */
    public long insert(PhotoInfo photoInfo){
        return  photoInfoDao.insert(photoInfo);
    }

    /**
     * 更新数据
     * @param photoInfo
     */
    public void update(PhotoInfo photoInfo){
        PhotoInfo mOldPersonInfor = photoInfoDao.queryBuilder().where(PhotoInfoDao.Properties.Id.eq(photoInfo.getId())).build().unique();//拿到之前的记录
        if(mOldPersonInfor !=null){
            mOldPersonInfor.setPhotoName("张三");
            photoInfoDao.update(mOldPersonInfor);
        }
    }
    /**
     * 按条件查询数据
     */
    public PhotoInfo searchByWhere(String wherecluse){
       PhotoInfo personInfors = photoInfoDao.queryBuilder().where(PhotoInfoDao.Properties.PhotoName.eq(wherecluse)).build().unique();
        return personInfors;
    }
    /**
     * 查询所有数据
     */
    public List<PhotoInfo> searchAll(){
        List<PhotoInfo>personInfors=photoInfoDao.queryBuilder().list();
        return personInfors;
    }
    /**
     * 删除数据
     */
    public void delete(String wherecluse){
        photoInfoDao.queryBuilder().where(PhotoInfoDao.Properties.PhotoName.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}